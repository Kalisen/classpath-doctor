package test.org.kalisen.classpathdoctor.adapter;

import java.util.Observable;
import java.util.Observer;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.kalisen.classpathdoctor.adapter.DefaultClassPathAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestDefaultClassPathAdapter {

	private static final String PATH_SEPARATOR = System
			.getProperty("path.separator");

	public void setClassPathShouldTriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.setClassPath("./");
		Assert.assertTrue(obs.hasBeenCalled,
				"The Observer should have been called");
	}

	public void setClassPathShouldNotTriggerAnEventIfTheOnlyDifferenceIsASeparatorAtTheEnd() {
		MockObserver obs = new MockObserver(null, false);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPath("./");
		adapter.addListener(obs);
		adapter.setClassPath("./" + PATH_SEPARATOR);
	}

	public void setClassPathShouldNotTriggerAnEventIfTheOnlyDifferenceIsASeparatorAtTheBeginning() {
		MockObserver obs = new MockObserver(null, false);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPath("./");
		adapter.addListener(obs);
		adapter.setClassPath(PATH_SEPARATOR + "./");
	}

	public void setClassPathShouldNotTriggerAnEventIfTheOnlyDifferenceIsASeparatorInTheMiddle() {
		MockObserver obs = new MockObserver(null, false);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPath("./" + PATH_SEPARATOR + "./");
		adapter.addListener(obs);
		adapter.setClassPath("./" + PATH_SEPARATOR + PATH_SEPARATOR + "./");
	}

	public void addAnEntryShouldTriggerAnEventContainingTheUpdatedClassPath()
			throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.addEntry("./");
		Assert.assertTrue(obs.hasBeenCalled, "The Observer should have been called");
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
				Assert.assertEquals(expectedClasspath, arg);
			} else {
				Assert.fail("Unexpected call to the observer");
			}
			this.hasBeenCalled = true;
		}
	}
}
