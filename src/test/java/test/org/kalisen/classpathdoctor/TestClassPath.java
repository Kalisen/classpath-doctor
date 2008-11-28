package test.org.kalisen.classpathdoctor;

import java.io.File;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestClassPath {

	public void equalsIsReflexive() {
		ClassPath path1 = new ClassPath();
		Assert.assertTrue(path1.equals(path1));
	}
	
	public void equalsIsTransitive() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./")));
		ClassPath path3 = new ClassPath();
		path3.addEntry(new DirectoryPath(new File("./")));
		Assert.assertTrue(path1.equals(path2));
		Assert.assertTrue(path2.equals(path3));
		Assert.assertTrue(path1.equals(path3));
	}

	public void equalsIsSymetric() {
		ClassPath path1 = new ClassPath();
		path1.addEntry(new DirectoryPath(new File("./")));
		ClassPath path2 = new ClassPath();
		path2.addEntry(new DirectoryPath(new File("./")));
		Assert.assertTrue(path1.equals(path2));
		Assert.assertTrue(path2.equals(path1));
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

	public void equalsShouldReturnFalseWhenComparingWithNull() {
		Assert.assertFalse(new ClassPath().equals(null));
	}

	public void equalsShouldReturnFalseWhenComparingWithAnInstanceOfADifferentClass() {
		Assert.assertFalse(new ClassPath().equals(new Object()));
	}
	
}
