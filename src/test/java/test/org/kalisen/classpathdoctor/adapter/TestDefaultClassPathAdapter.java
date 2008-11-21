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
	
	public void setClassPathShouldTriggerAnEventContainingTheUpdatedClassPath() throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));

		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.setClassPath("./");
		Assert.assertTrue(obs.hasBeenCalled, "The Observer hasn't been called");
	}
	
	public void addAnEntryShouldTriggerAnEventContainingTheUpdatedClassPath() throws Exception {
		final ClassPath expectedClasspath = new ClassPath();
		expectedClasspath.addEntry(new DirectoryPath("."));
		
		MockObserver obs = new MockObserver(expectedClasspath);
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.addListener(obs);
		adapter.addEntry("./");
		Assert.assertTrue(obs.hasBeenCalled, "The Observer hasn't been called");
	}
	
	private class MockObserver implements Observer {
		public boolean hasBeenCalled = false;
		private ClassPath expectedClasspath = null;
		
		public MockObserver(ClassPath expectedClassPath) {
			this.expectedClasspath = expectedClassPath;
		}
		
		public void update(Observable o, Object arg) {
			Assert.assertEquals(expectedClasspath, arg);
			this.hasBeenCalled = true;
		}
	}
}
