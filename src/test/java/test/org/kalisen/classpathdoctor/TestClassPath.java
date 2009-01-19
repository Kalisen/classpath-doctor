package test.org.kalisen.classpathdoctor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.kalisen.classpathdoctor.EmptyPathEntry;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.test.TestEqualHelper;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test
public class TestClassPath {

	private final static PathEntry ENTRY = new DirectoryPath(".");
	private final static PathEntry ENTRY2 = new DirectoryPath("..");

	public void testSettingEntries() {
		ClassPath cp = new ClassPath();
		ArrayList<PathEntry> newEntries = new ArrayList<PathEntry>();
		newEntries.add(ENTRY);
		newEntries.add(ENTRY2);
		newEntries.add(ENTRY);
		cp.setEntries(newEntries);
		List<PathEntry> entries = cp.getEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(entries.size(), 3);
		Iterator<PathEntry> iterOnEntries = entries.iterator();
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
		Assert.assertEquals(iterOnEntries.next(), ENTRY2);
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
	}

	public void testAddEntry() {
		ClassPath cp = new ClassPath();
		cp.addEntry(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(entries.size(), 1);
		Assert.assertEquals(entries.iterator().next(), ENTRY);
	}

	public void testRemoveAnEntry() {
		ClassPath cp = new ClassPath();
		cp.addEntry(ENTRY);
		cp.addEntry(ENTRY2);
		cp.addEntry(ENTRY);
		cp.removeEntry(ENTRY);
		List<PathEntry> entries = cp.getEntries();
		Assert.assertNotNull(entries);
		Assert.assertEquals(entries.size(), 2);
		Iterator<PathEntry> iterOnEntries = entries.iterator();
		Assert.assertEquals(iterOnEntries.next(), ENTRY2);
		Assert.assertEquals(iterOnEntries.next(), ENTRY);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void addingANullEntryWillThrowAnIllegalArgumentException() {
		new ClassPath().addEntry(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void removingANullEntryWillThrowAnIllegalArgumentException() {
		new ClassPath().removeEntry(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void settingNullEntriesWillThrowAnIllegalArgumentException() {
		new ClassPath().setEntries(null);
	}

	public void equalsShouldReturnTrueWhenClassPathAreTheSame() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./")));
		Assert.assertTrue(path1.equals(path2));
	}

	public void equalsShouldReturnFalseWhenClassPathHaveDifferentNumberOfEntries() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		Assert.assertFalse(path1.equals(new ClassPath()));
	}

	public void equalsShouldReturnFalseWhenClassPathEntriesAreDifferent() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./dummy.jar")));
		Assert.assertFalse(path1.equals(path2));
	}

	@Test
	public void equalsIgnoreEmptyEntriesIsReflexive() {
		ClassPath cp1 = buildTestClassPathWithEmptyEntries();
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp1));
	}

	@Test
	public void equalsIgnoreEmptyEntriesIsSymetric() {
		ClassPath cp1 = buildTestClassPath();
		ClassPath cp2 = buildTestClassPathWithEmptyEntries();
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp2));
		Assert.assertTrue(cp2.equalsIgnoreEmptyEntries(cp1));
	}

	@Test
	public void equalsIgnoreEmptyEntriesIsTransitive() {
		ClassPath cp1 = buildTestClassPath();
		ClassPath cp2 = buildTestClassPathWithEmptyEntries();
		ClassPath cp3 = buildTestClassPathWithEmptyEntries2();
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp2));
		Assert.assertTrue(cp2.equalsIgnoreEmptyEntries(cp3));
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp3));
	}

	@Test
	public void equalsIgnoreEmptyEntriesToNullShouldReturnFalse() {
		ClassPath cp1 = buildTestClassPathWithEmptyEntries();
		Assert.assertFalse(cp1.equalsIgnoreEmptyEntries(null));
	}

	@Test
	public void equalsIgnoreEmptyEntriesShouldBeConsistentFromCallToCall() {
		ClassPath cp1 = buildTestClassPathWithEmptyEntries();
		ClassPath cp2 = buildTestClassPathWithEmptyEntries2();
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp2));
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp2));
		Assert.assertTrue(cp1.equalsIgnoreEmptyEntries(cp2));
	}

	@Test
	public void equalsShouldReturnFalseWhenComparingAnEmpyClassPathAndANonEmptyOne() {
		ClassPath cp1 = new ClassPath();
		ClassPath cp2 = buildTestClassPathWithEmptyEntries();
		Assert.assertFalse(cp1.equalsIgnoreEmptyEntries(cp2));
	}

	@Factory
	public Object[] buildEqualHashCodeTest() {
		return new Object[] { new TestEqualHelper(buildTestClassPath(),
				buildTestClassPath(), buildTestClassPath()) };
	}

	public ClassPath buildTestClassPath() {
		ClassPath result = new ClassPath();
		result.addEntry(new DirectoryPath("./"));
		result.addEntry(new DirectoryPath("../"));
		return result;
	}

	public ClassPath buildTestClassPathWithEmptyEntries() {
		ClassPath result = new ClassPath();
		result.addEntry(EmptyPathEntry.INSTANCE);
		result.addEntry(new DirectoryPath("./"));
		result.addEntry(new DirectoryPath("../"));
		result.addEntry(EmptyPathEntry.INSTANCE);
		return result;
	}

	public ClassPath buildTestClassPathWithEmptyEntries2() {
		ClassPath result = new ClassPath();
		result.addEntry(new DirectoryPath("./"));
		result.addEntry(EmptyPathEntry.INSTANCE);
		result.addEntry(new DirectoryPath("../"));
		return result;
	}
}
