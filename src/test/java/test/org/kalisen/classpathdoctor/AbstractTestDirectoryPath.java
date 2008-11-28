package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.DirectoryPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public abstract class AbstractTestDirectoryPath {

	public void equalsIsReflexive() {
		DirectoryPath path1 = new DirectoryPath(".", getTestedFileSeparator());
		Assert.assertTrue(path1.equals(path1));
	}
	
	public void equalsIsTransitive() {
		DirectoryPath path1 = new DirectoryPath(".", getTestedFileSeparator());
		DirectoryPath path2 = new DirectoryPath(".", getTestedFileSeparator());
		DirectoryPath path3 = new DirectoryPath(".", getTestedFileSeparator());
		Assert.assertTrue(path1.equals(path2));
		Assert.assertTrue(path2.equals(path3));
		Assert.assertTrue(path1.equals(path3));
	}

	public void equalsIsSymetric() {
		DirectoryPath path1 = new DirectoryPath(".", getTestedFileSeparator());
		DirectoryPath path2 = new DirectoryPath(".", getTestedFileSeparator());
		Assert.assertTrue(path1.equals(path2));
		Assert.assertTrue(path2.equals(path1));
	}

	public void equalsShouldIgnoreTheTrailingFileSeparator() {
		DirectoryPath path1 = new DirectoryPath(".", getTestedFileSeparator());
		DirectoryPath path2 = new DirectoryPath("." + getTestedFileSeparator(), getTestedFileSeparator());
		Assert.assertTrue(path1.equals(path2));
	}

	public void hashCodesShouldBeEqualForTwoEqualObjects() {
		DirectoryPath path1 = new DirectoryPath(".", getTestedFileSeparator());
		DirectoryPath path2 = new DirectoryPath("." + getTestedFileSeparator(), getTestedFileSeparator());
		Assert.assertTrue(path1.equals(path2));
		Assert.assertEquals(path1.hashCode(), path2.hashCode());
	}
	
	protected abstract String getTestedFileSeparator();

}
