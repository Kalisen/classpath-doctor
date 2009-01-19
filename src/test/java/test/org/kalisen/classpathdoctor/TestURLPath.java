package test.org.kalisen.classpathdoctor;

import java.net.URL;

import org.kalisen.classpathdoctor.DefaultVersion;
import org.kalisen.classpathdoctor.URLPath;
import org.kalisen.classpathdoctor.Version;
import org.kalisen.test.TestEqualHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

@Test
public class TestURLPath {

	private URL url = null;
	private URLPath testedURLPath = null;

	@BeforeMethod
	protected void setUp() throws Exception {
		this.url = this.getClass().getClassLoader().getResource(
				"TestURLPath-resource.txt");
		this.testedURLPath = new URLPath(this.url);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void urlPathConstructorThrowsIllegalArgumentExceptionFornullParameter() {
		new URLPath(null);
	}

	public void testGetVersion() {
		Version v = this.testedURLPath.getVersion();
		Assert.assertEquals(v, new DefaultVersion(0, 0, 0));
	}

	public void testGetPath() throws Exception {
		String urlString = this.testedURLPath.getPath();
		URL newURL = new URL(urlString);
		Assert.assertEquals(newURL, this.url);
	}

	public void testExistsWhenURLDoesntExist() throws Exception {
		URL url = new URL("file://dummydumdum.dum");
		URLPath tested = new URLPath(url);
		Assert.assertFalse(tested.exists());
	}

	public void testExistsWhenURLDoesExist() {
		Assert.assertTrue(this.testedURLPath.exists());
	}

	public void testGetUrl() {
		Assert.assertEquals(this.testedURLPath.getUrl(), this.url);
	}

	public void testSetUrl() throws Exception {
		Assert.assertEquals(this.testedURLPath.getUrl(), this.url);
		URL newURL = new URL("http://127.0.0.1");
		this.testedURLPath.setUrl(newURL);
		Assert.assertEquals(this.testedURLPath.getUrl(), newURL);
	}

	@Factory
	public Object[] createEqualHashCodeTest() {
		URL resourceUrl = this.getClass().getClassLoader().getResource(
				"TestURLPath-resource.txt");
		Object[] result = new Object[] { new TestEqualHelper(new URLPath(
				resourceUrl), new URLPath(resourceUrl),
				new URLPath(resourceUrl)) };
		return result;
	}

}
