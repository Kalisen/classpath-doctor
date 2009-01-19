package test.org.kalisen.classpathdoctor.gui.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.kalisen.classpathdoctor.gui.actions.AddAnEntryAction;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TestJavaLibraryFileFilter {

	public void theFilterShouldAcceptDirectories() throws Exception {
		File temp = File
				.createTempFile(this.getClass().getSimpleName(), ".tmp");
		File dir = temp.getParentFile();
		temp.deleteOnExit();
		FileFilter filter = new AddAnEntryAction.JavaLibraryFileFilter();
		Assert.assertTrue(filter.accept(dir));
	}

	public void theFilterShouldAcceptJarFiles() throws Exception {
		File f = File.createTempFile(this.getClass().getSimpleName(), ".jar");
		FileFilter filter = new AddAnEntryAction.JavaLibraryFileFilter();
		Assert.assertTrue(filter.accept(f));
	}

	public void theFilterShouldAcceptZipFiles() throws Exception {
		File f = File.createTempFile(this.getClass().getSimpleName(), ".zip");
		FileFilter filter = new AddAnEntryAction.JavaLibraryFileFilter();
		Assert.assertTrue(filter.accept(f));
	}

	public void theFilterShouldNotAcceptNullInput() {
		FileFilter filter = new AddAnEntryAction.JavaLibraryFileFilter();
		Assert.assertFalse(filter.accept(null));
	}

	public void theFilterShouldNotAcceptOtherFiles() throws Exception {
		File f = File.createTempFile(this.getClass().getSimpleName(), ".xyz");
		FileFilter filter = new AddAnEntryAction.JavaLibraryFileFilter();
		Assert.assertFalse(filter.accept(f));
	}
}
