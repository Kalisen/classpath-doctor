package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.kalisen.classpathdoctor.ClassPathError;
import org.kalisen.classpathdoctor.ClassPathValidator;
import org.kalisen.classpathdoctor.ValidationReport;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestClassPathValidator {

	private static final String PATH_SEPARATOR = System
			.getProperty("path.separator");

	private static final String JAR1_NAME = "JAR1";
	private static final String JAR2_NAME = "JAR2";
	private static final String JAR3_NAME = "JAR3";

	private File jar1 = null;
	private File jar2 = null;
	private File jar3 = null;

	@Test
	public void validatingNullClasspathShouldReturnNoError() {
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = null;
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertNotNull(result.getErrors());
		assertTrue(result.getErrors().isEmpty());
	}

	@Test
	public void validatingAnEmptyStringShouldReturnNoError() {
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = "";
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertNotNull(result.getErrors());
		assertTrue(result.getErrors().isEmpty());
	}

	@Test
	public void validatingAStringMadeOfSpacesShouldReturnNoError() {
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = "        ";
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertTrue(result.isOk());
		assertNotNull(result.getErrors());
		assertTrue(result.getErrors().isEmpty());
	}

	@Test
	public void validatingNonEmptyClasspathWithMissingJarShouldReturnAnError() {
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = this.jar1.getPath() + PATH_SEPARATOR
				+ this.jar2.getPath() + PATH_SEPARATOR + "missing.jar"
				+ PATH_SEPARATOR + this.jar3.getPath();
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertFalse(result.isOk());
		List<ClassPathError> errors = result.getErrors();
		assertNotNull(errors);
		assertEquals(errors.size(), 1);
		assertEquals("missing.jar", errors.get(0).getResourceName());
	}

	@Test
	public void validatingNonEmptyClasspathWithNoMissingJarShouldReturnNoError() {
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = this.jar1.getPath() + PATH_SEPARATOR
				+ this.jar2.getPath() + PATH_SEPARATOR + this.jar3.getPath();
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertTrue(result.isOk());
		List<ClassPathError> errors = result.getErrors();
		assertNotNull(errors);
		assertTrue(errors.isEmpty());
	}

	@Test
	public void validatingNonEmptyClasspathUsingEnvVariableShouldReturnNoError() {
		String classPathEntry = getVariableBasedEntry();
		ClassPathValidator validator = new ClassPathValidator();
		String classpath = this.jar1.getPath() + PATH_SEPARATOR
				+ classPathEntry + PATH_SEPARATOR + this.jar2.getPath()
				+ PATH_SEPARATOR + this.jar3.getPath();
		ValidationReport result = validator.validate(classpath);
		assertNotNull(result);
		assertTrue(result.isOk());
		List<ClassPathError> errors = result.getErrors();
		assertNotNull(errors);
		assertTrue(errors.isEmpty());
	}

	private String getVariableBasedEntry() {
		String result = null;
		String osName = System.getProperty("os.name");
		if (osName != null && osName.startsWith("Windows")) {
			result = System.getenv().get("HOMEPATH");
		} else {
			result = System.getenv().get("HOME");
		}
		return result;
	}

	@SuppressWarnings("unused")
	@BeforeClass
	private void createJarsEntries() throws IOException {
		this.jar1 = File.createTempFile(JAR1_NAME, "jar");
		this.jar2 = File.createTempFile(JAR2_NAME, "jar");
		this.jar3 = File.createTempFile(JAR3_NAME, "jar");
	}

	@SuppressWarnings("unused")
	@AfterClass
	private void cleanupJarsEntries() {
		this.jar1.delete();
		this.jar2.delete();
		this.jar3.delete();
		this.jar1 = null;
		this.jar2 = null;
		this.jar3 = null;
	}
}
