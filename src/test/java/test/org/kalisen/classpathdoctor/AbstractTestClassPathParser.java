package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import mockit.Mockit;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.ClassPathParser;
import org.kalisen.classpathdoctor.EmptyPathEntry;
import org.kalisen.classpathdoctor.Environment;
import org.kalisen.classpathdoctor.InvalidPathEntry;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.classpathdoctor.PathResolver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public abstract class AbstractTestClassPathParser {

	private static final String VAR_NAME = "MY_VARIABLE";
	private static final String VAR_VALUE = "MY_VALUE";

	@AfterTest
	public void tearDown() {
		Mockit.assertExpectations();
		Mockit.tearDownMocks();
	}

	@Test
	public void thePathSeparatorSetInTheConstructorShouldBeRetrievedThroughTheGetMethod() {
		final String pathSeparator = "%";
		ClassPathParser parser = new ClassPathParser(pathSeparator);
		assertEquals(parser.getPathSeparatorAsString(), pathSeparator);
	}

	@Test
	public void byDefaultThePathSeparatorShouldBeTheOneOfTheCurrentPlatform() {
		ClassPathParser parser = new ClassPathParser();
		assertEquals(parser.getPathSeparatorAsString(), System
				.getProperty("path.separator"));
	}

	@Test
	public void parsingClasspathMadeOfSpacesShouldReturnEmptyClasspath() {
		ClassPathParser parser = new ClassPathParser();
		ClassPath path = parser.parse("           ");
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
	}

	@Test
	public void parsingEmptyClasspathShouldReturnEmptyClasspath() {
		ClassPathParser parser = new ClassPathParser();
		ClassPath path = parser.parse("");
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
	}

	@Test
	public void parsingAnEmptyStringBetweenTwoSeparatorsShouldReturnAnInvalidEntry() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		ClassPath path = parser.parse("something" + getTestedPathSeparator()
				+ "   " + getTestedPathSeparator() + "something_else");
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 3);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next().getPath(), "something");
		PathEntry invalid = iter.next();
		assertEquals(invalid.getPath(), "   ");
		assertTrue(invalid instanceof InvalidPathEntry);
		assertEquals(iter.next().getPath(), "something_else");
	}

	@Test
	public void parsingASpaceInFrontOfASeparatorShouldReturnAClasspathWithASingleInvalidEntry() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		ClassPath path = parser.parse(" " + getTestedPathSeparator());
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 2);
		Iterator<PathEntry> iter = entries.iterator();
		PathEntry entry = iter.next();
		assertNotNull(entry);
		assertTrue(entry instanceof InvalidPathEntry);
		assertEquals(entry.getPath(), " ");
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parsingAnEmptyStringInFrontOfASeparatorShouldReturnTwoEmptyClassPathEntries() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		ClassPath path = parser.parse("" + getTestedPathSeparator() + "");
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 2);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parsingAnEmptyStringInFrontOfASeparatorFollowedByAnEntryShouldReturnClasspathWith2Entries() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		ClassPath path = parser.parse("" + getTestedPathSeparator() + "entry1");
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 2);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
		PathEntry entry = iter.next();
		assertNotNull(entry);
		assertEquals(entry.getPath(), "entry1");
	}

	@Test
	public void parsingNullClasspathShouldReturnEmptyClasspath() {
		ClassPathParser parser = new ClassPathParser();
		ClassPath path = parser.parse(null);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 0);
	}

	@Test
	public void parsingValidClasspathStringShouldReturnNonEmptyClasspath() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		String cpString = "entry1" + getTestedPathSeparator() + "entry2"
				+ getTestedPathSeparator() + "entry3"
				+ getTestedPathSeparator() + "entry4"
				+ getTestedPathSeparator();
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 5);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next().getPath(), "entry1");
		assertEquals(iter.next().getPath(), "entry2");
		assertEquals(iter.next().getPath(), "entry3");
		assertEquals(iter.next().getPath(), "entry4");
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parsingValidClasspathStringWithFileSeparatorShouldReturnNonEmptyClasspath() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		String cpString = "entry1" + getTestedPathSeparator() + "entry2"
				+ getTestedPathSeparator() + "entry3"
				+ getTestedFileSeparator() + "subentry3"
				+ getTestedPathSeparator() + "entry4"
				+ getTestedPathSeparator();
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 5);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next().getPath(), "entry1");
		assertEquals(iter.next().getPath(), "entry2");
		assertEquals(iter.next().getPath(), "entry3" + getTestedFileSeparator()
				+ "subentry3");
		assertEquals(iter.next().getPath(), "entry4");
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parsingValidClasspathStringUsingVariableShouldReturnNonEmptyClasspathAndSubstitueVariableValue() {
		Mockit.redefineMethods(Environment.class, MockEnvironment.class);
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		PathResolver pathResolver = new PathResolver();
		pathResolver.setVariableResolver(getTestedVariableResolver());
		parser.setPathResolver(pathResolver);
		String cpString = "entry1" + getTestedPathSeparator()
				+ getTestedVariableReference() + getTestedPathSeparator()
				+ "entry3" + getTestedFileSeparator() + "subentry3"
				+ getTestedPathSeparator() + "entry4"
				+ getTestedPathSeparator();
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 5);
		Iterator<PathEntry> iter = entries.iterator();
		assertEquals(iter.next().getPath(), "entry1");
		assertEquals(iter.next().getPath(), VAR_VALUE);
		assertEquals(iter.next().getPath(), "entry3" + getTestedFileSeparator()
				+ "subentry3");
		assertEquals(iter.next().getPath(), "entry4");
		assertEquals(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parserShouldHandleConsecutiveSeparators() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		String cpString = getTestedPathSeparator() + "entry2"
				+ getTestedPathSeparator() + getTestedPathSeparator()
				+ "entry3";
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 4);
		Iterator<PathEntry> iter = entries.iterator();
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
		Assert.assertEquals(iter.next().getPath(), "entry2");
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
		Assert.assertEquals(iter.next().getPath(), "entry3");
	}

	@Test
	public void parserShouldHandleConsecutiveHeadingSeparators() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		String cpString = getTestedPathSeparator() + getTestedPathSeparator()
				+ "entry2" + getTestedPathSeparator() + "entry3"
				+ getTestedPathSeparator();
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 5);
		Iterator<PathEntry> iter = entries.iterator();
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
		Assert.assertEquals(iter.next().getPath(), "entry2");
		Assert.assertEquals(iter.next().getPath(), "entry3");
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
	}

	@Test
	public void parserShouldHandleConsecutiveTrailingSeparators() {
		ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
		String cpString = "entry2" + getTestedPathSeparator() + "entry3"
				+ getTestedPathSeparator() + getTestedPathSeparator();
		ClassPath path = parser.parse(cpString);
		List<PathEntry> entries = path.getEntries();
		assertNotNull(entries);
		assertEquals(entries.size(), 4);
		Iterator<PathEntry> iter = entries.iterator();
		Assert.assertEquals(iter.next().getPath(), "entry2");
		Assert.assertEquals(iter.next().getPath(), "entry3");
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
		Assert.assertSame(iter.next(), EmptyPathEntry.INSTANCE);
	}

	protected abstract String getTestedPathSeparator();

	protected abstract String getTestedFileSeparator();

	protected abstract String getTestedVariableReference();

	protected abstract AbstractVariableResolver getTestedVariableResolver();

	public static class MockEnvironment {
		public String getValue(String variable) {
			if (VAR_NAME.equals(variable)) {
				return AbstractTestClassPathParser.VAR_VALUE;
			}
			return null;
		}
	}

}
