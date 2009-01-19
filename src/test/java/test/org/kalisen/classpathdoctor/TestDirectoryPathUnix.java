package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.DirectoryPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestDirectoryPathUnix extends AbstractTestDirectoryPath {

	@Override
	protected String getTestedFileSeparator() {
		return "/";
	}

	public void equalsShouldNotIgnoreTheTrailingFileSeparatorWhenItHappenToBeTheRootDirectory() {
		DirectoryPath path1 = new DirectoryPath("/", getTestedFileSeparator());
		DirectoryPath path2 = new DirectoryPath("", getTestedFileSeparator());
		Assert.assertFalse(path1.equals(path2));
	}

}
