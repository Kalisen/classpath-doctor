package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.PathSeparator;
import org.kalisen.test.TestEqualHelper;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test
public class TestPathSeparator {

	public void testToString() {
		String separatorString = ";";
		PathSeparator sep = new PathSeparator(separatorString);
		Assert.assertEquals(separatorString, sep.toString());
	}

	@Factory
	public Object[] createEqualHashCodeTest() {
		Object[] result = new Object[] { new TestEqualHelper(
				new PathSeparator(";"), new PathSeparator(";"), new PathSeparator(";"))};
		return result;
	}
}
