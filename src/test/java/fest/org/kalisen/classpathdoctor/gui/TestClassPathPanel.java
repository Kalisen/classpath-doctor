package fest.org.kalisen.classpathdoctor.gui;

import java.io.File;

import javax.swing.JButton;

import org.fest.swing.annotation.GUITest;
import org.fest.swing.fixture.FrameFixture;
import org.kalisen.classpathdoctor.DirectoryPath;
import org.kalisen.classpathdoctor.gui.ClassPathDoctorGUI;
import org.kalisen.test.fest.AbstractButtonLabelMatcher;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
@GUITest
public class TestClassPathPanel {
	private final static String SEPARATOR = System.getProperty("path.separator");
	private final static DirectoryPath CURRENT_DIR = new DirectoryPath(new File("."));
	private final static DirectoryPath PARENT_DIR = new DirectoryPath(new File(".."));
	
	
	private FrameFixture frame = null;

	@BeforeMethod
	protected void setUp() {
		this.frame = new FrameFixture(new ClassPathDoctorGUI());
		this.frame.show();
	}

	public void testAddAnEntryButton() {
		String path = ".";
		addEntry(path);
		this.frame.list().item(0).equals(CURRENT_DIR);
		this.frame.textBox().requireText(CURRENT_DIR.getFile().getAbsolutePath());
	}
	
	public void testRemoveAnEntryButton() {
		addEntry(".");
		addEntry("..");
		addEntry(".");
		this.frame.list().selectItem(0);
		this.frame.button(new AbstractButtonLabelMatcher<JButton>(JButton.class, "Remove")).click();
		this.frame.textBox().requireText(PARENT_DIR.getFile().getAbsolutePath() + SEPARATOR + CURRENT_DIR.getFile().getAbsolutePath());
		this.frame.list().requireNoSelection();
		this.frame.list().item(0).equals(new DirectoryPath(".."));
		this.frame.list().item(1).equals(new DirectoryPath("."));

		this.frame.list().selectItem(0);
		this.frame.button(new AbstractButtonLabelMatcher<JButton>(JButton.class, "Remove")).click();
		this.frame.textBox().requireText(CURRENT_DIR.getFile().getAbsolutePath());
		this.frame.list().requireNoSelection();
		this.frame.list().item(0).equals(new DirectoryPath("."));
		this.frame.list().selectItem(0);

		this.frame.list().selectItem(0);
		this.frame.button(new AbstractButtonLabelMatcher<JButton>(JButton.class, "Remove")).click();
		this.frame.textBox().requireEmpty();
		this.frame.list().requireNoSelection();
		Assert.assertTrue(this.frame.list().contents().length == 0);
	}

	public void testRemoveAnEntryWithCurrentAndParentDirectories() {
		this.frame.textBox().setText("." + SEPARATOR + ".." + SEPARATOR + "." + SEPARATOR + ".."); 
		this.frame.list().selectItem(0).equals(CURRENT_DIR);
		this.frame.list().selectItem(0).equals(PARENT_DIR);
		this.frame.list().selectItem(0).equals(CURRENT_DIR);
		this.frame.list().selectItem(0).equals(PARENT_DIR);
		//then remove the first entry
		this.frame.list().selectItem(0);
		this.frame.button(new AbstractButtonLabelMatcher<JButton>(JButton.class, "Remove")).click();
		this.frame.textBox().requireText(".." + SEPARATOR + "." + SEPARATOR + ".."); 
		//then remove the first entry again
		this.frame.list().selectItem(0);
		this.frame.button(new AbstractButtonLabelMatcher<JButton>(JButton.class, "Remove")).click();
		this.frame.textBox().requireText("." + SEPARATOR + ".."); 
	}
	
	protected void addEntry(String path) {
		File dir = new File(path);
		this.frame.button(
				new AbstractButtonLabelMatcher<JButton>(JButton.class, "Add"))
				.click();
		this.frame.fileChooser().setCurrentDirectory(CURRENT_DIR.getFile()).selectFile(dir).approve();
	}
	
	@AfterMethod
	public void tearDown() {
		this.frame.cleanUp();
	}

}
