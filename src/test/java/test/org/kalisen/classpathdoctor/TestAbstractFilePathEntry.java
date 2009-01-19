package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.AbstractFilePathEntry;
import org.kalisen.classpathdoctor.JarPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestAbstractFilePathEntry {

	public void equalsShouldBeReflexive() {
		AbstractFilePathEntry entry1 = new JarPath("./dummy.jar");
		Assert.assertTrue(entry1.equals(entry1));
	}

	public void equalsShouldBeTransitive() {
		AbstractFilePathEntry entry1 = new JarPath("./dummy.jar");
		AbstractFilePathEntry entry2 = new JarPath("./dummy.jar");
		AbstractFilePathEntry entry3 = new JarPath("./dummy.jar");
		Assert.assertTrue(entry1.equals(entry2));
		Assert.assertTrue(entry2.equals(entry3));
		Assert.assertTrue(entry1.equals(entry3));
	}

	public void equalsShouldBeSymetric() {
		AbstractFilePathEntry entry1 = new JarPath("./dummy.jar");
		AbstractFilePathEntry entry2 = new JarPath("./dummy.jar");
		Assert.assertTrue(entry1.equals(entry2));
		Assert.assertTrue(entry2.equals(entry1));
	}
}
