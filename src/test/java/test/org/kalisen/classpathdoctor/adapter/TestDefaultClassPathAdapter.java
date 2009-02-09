package test.org.kalisen.classpathdoctor.adapter;

import java.util.Observable;
import java.util.Observer;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.kalisen.classpathdoctor.EmptyPathEntry;
import org.kalisen.classpathdoctor.adapter.DefaultClassPathAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestDefaultClassPathAdapter {

	private static final String PATH_SEPARATOR = System
			.getProperty("path.separator");
	private static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	private static final String CURRENT_DIR = "." + FILE_SEPARATOR;

	public void setClassPathAsTextShouldTriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.setClassPathAsText(CURRENT_DIR);
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}

	public void setClassPathAsTextShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryAtTheEnd() {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));
		expectedClasspath.addEntry(EmptyPathEntry.INSTANCE);
		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPathAsText(CURRENT_DIR);
		adapter.addListener(obs);
		adapter.setClassPathAsText(CURRENT_DIR + PATH_SEPARATOR);
	}

	public void setClassPathAsTextShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryAtTheBeginning() {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(EmptyPathEntry.INSTANCE);
		expectedClasspath.addEntry(new DirectoryPath("."));
		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPathAsText(CURRENT_DIR);
		adapter.addListener(obs);
		adapter.setClassPathAsText(PATH_SEPARATOR + CURRENT_DIR);
	}

	public void setClassPathAsTextShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryInTheMiddle() {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));
		expectedClasspath.addEntry(EmptyPathEntry.INSTANCE);
		expectedClasspath.addEntry(new DirectoryPath("."));
		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPathAsText(CURRENT_DIR + PATH_SEPARATOR + "."
				+ FILE_SEPARATOR);
		adapter.addListener(obs);
		adapter.setClassPathAsText(CURRENT_DIR + PATH_SEPARATOR
				+ PATH_SEPARATOR + CURRENT_DIR);
	}

	public void addAnEntryShouldTriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.addEntry(CURRENT_DIR);
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}

	public void addAnEntryTwiceShouldTriggerAnEventContainingTheUpdatedClassPathContainingBothEntries()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		adapter.addListener(obs);
		adapter.addEntry(CURRENT_DIR);
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}

	public void removeAnEntryShouldtriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		adapter.addEntry(CURRENT_DIR);
		adapter.addListener(obs);
		adapter.removeEntry(CURRENT_DIR);
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}
	
	public void removeTheFirstEntryShouldRemoveTheSeparatorAsWell() {
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		adapter.addEntry(CURRENT_DIR);
		adapter.removeEntry(CURRENT_DIR);
		Assert.assertEquals(adapter.getClassPathAsText(), CURRENT_DIR); 
	}
	
	public void removeAnEntryInTheMiddleOfTheClassPathShouldRemoveUnnecessarySeparators() {
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		adapter.addEntry("..");
		adapter.addEntry(CURRENT_DIR);
		adapter.removeEntry("..");
		Assert.assertEquals(adapter.getClassPathAsText(), CURRENT_DIR + PATH_SEPARATOR + CURRENT_DIR); 
	}
	
	public void removeAnEntryShouldOnlyRemoveTheFirstOccurence() {
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		adapter.addEntry("..");
		adapter.addEntry(CURRENT_DIR);
		adapter.removeEntry(CURRENT_DIR);
		Assert.assertEquals(adapter.getClassPathAsText(), ".." + PATH_SEPARATOR + CURRENT_DIR); 
	}

	public void testGetClassPathAsText() {
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addEntry(CURRENT_DIR);
		Assert.assertEquals(adapter.getClassPathAsText(), "./");
		adapter.setClassPathAsText("../");
		Assert.assertEquals(adapter.getClassPathAsText(), "../");
	}

	public void setClassPathShouldTriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.setClassPath(expectedClasspath);
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}

	public void setClassPathShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryAtTheEnd() {
		ClassPath newCP = new ClassPath();
		newCP.addEntry(new DirectoryPath("."));
		newCP.addEntry(EmptyPathEntry.INSTANCE);
		MockObserver obs = new MockObserver(newCP);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		ClassPath cp = new ClassPath();
		cp.addEntry(new DirectoryPath("."));
		adapter.setClassPath(cp);
		adapter.addListener(obs);
		adapter.setClassPath(newCP);
	}

	public void setClassPathShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryAtTheBeginning() {
		ClassPath newCP = new ClassPath();
		newCP.addEntry(EmptyPathEntry.INSTANCE);
		newCP.addEntry(new DirectoryPath("."));
		MockObserver obs = new MockObserver(newCP);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		ClassPath cp = new ClassPath();
		cp.addEntry(new DirectoryPath("."));
		adapter.setClassPath(cp);
		adapter.addListener(obs);
		adapter.setClassPath(newCP);
	}

	public void setClassPathShouldTriggerAnEventIfTheOnlyDifferenceIsAnEmptyEntryInTheMiddle() {
		ClassPath newCP = new ClassPath();
		newCP.addEntry(new DirectoryPath("."));
		newCP.addEntry(EmptyPathEntry.INSTANCE);
		newCP.addEntry(new DirectoryPath("."));
		MockObserver obs = new MockObserver(newCP);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		ClassPath cp = new ClassPath();
		cp.addEntry(new DirectoryPath("."));
		cp.addEntry(new DirectoryPath("."));
		adapter.setClassPath(cp);
		adapter.addListener(obs);
		adapter.setClassPath(newCP);
	}

	private class MockObserver implements Observer {
		public boolean hasBeenCalled = false;
		private ClassPath expectedClasspath = null;
		private boolean isCallExpected = true;

		public MockObserver(ClassPath expectedClassPath) {
			this.expectedClasspath = expectedClassPath;
		}

		public MockObserver(ClassPath expectedClassPath, boolean isCallExpected) {
			this.expectedClasspath = expectedClassPath;
			this.isCallExpected = isCallExpected;
		}

		public void update(Observable o, Object arg) {
			if (this.isCallExpected) {
				Assert.assertEquals(this.expectedClasspath, arg);
			} else {
				Assert.fail("Unexpected call to the observer");
			}
			this.hasBeenCalled = true;
		}
	}
}
