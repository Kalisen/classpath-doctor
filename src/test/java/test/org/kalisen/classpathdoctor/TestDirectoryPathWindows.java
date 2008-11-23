package test.org.kalisen.classpathdoctor;

public class TestDirectoryPathWindows extends AbstractTestDirectoryPath {

	@Override
	protected String getTestedFileSeparator() {
		return "\\";
	}

}
