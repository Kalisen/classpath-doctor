package test.org.kalisen.classpathdoctor.adapter;

import java.util.Observable;
import java.util.Observer;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.adapter.DefaultClassPathAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestDefaultClassPathAdapter {
	
	public void setClassPathShouldTriggerAnEventContainingTheUpdatedClassPath() {
		final ClassPath expectedClasspath = new ClassPath();
		Observer obs = new Observer() {
		
			public void update(Observable o, Object arg) {
				Assert.assertEquals(expectedClasspath, arg);
			}
		};
		DefaultClassPathAdapter adapter = new DefaultClassPathAdapter();
		adapter.setClassPath("");
	}
	
	public void addAnEntryShouldTriggerAnEventContainingTheUpdatedClassPath() {
		Assert.fail("not yet implemented");
	}
}
