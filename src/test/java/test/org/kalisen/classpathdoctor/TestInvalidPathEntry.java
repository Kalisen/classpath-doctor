package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.InvalidPathEntry;
import org.kalisen.classpathdoctor.Version;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestInvalidPathEntry {

    @Test
    public void getPathShouldReturnAStringEvenWhenPathIsNull() {
        InvalidPathEntry invalid = new InvalidPathEntry(null);
        assertEquals(invalid.getPath(), "null");
    }

    @Test
    public void getPathShouldReturnTheStringPath() {
        final String path = "sometwisted   path";
        InvalidPathEntry invalid = new InvalidPathEntry(path);
        assertEquals(invalid.getPath(), path);
    }

    @Test
    public void getVersionShouldReturnAZeroVersion() {
        InvalidPathEntry invalid = new InvalidPathEntry(null);
        Version v = invalid.getVersion();
        assertNotNull(v);
        assertEquals(v.toString(), "0.0.0");
    }

    @Test
    public void isExistShouldReturnFalse() {
        Assert.assertFalse(new InvalidPathEntry(null).exists());
    }

    @Test
    public void toStringShouldReturnThePathIfTheMessageIsNull() {
        final String path = "sometwisted   path";
        InvalidPathEntry invalid = new InvalidPathEntry(path);
        assertEquals(invalid.toString(), path);
    }

    @Test
    public void toStringShouldHaveTheSameBehaviorAsGetPathForNullPath() {
        final String path = null;
        InvalidPathEntry invalid = new InvalidPathEntry(path);
        assertEquals(invalid.toString(), "null");
    }

    @Test
    public void toStringShouldReturnTheMessagePlusThePathIfTheMessageIsNotNull() {
        final String path = "sometwisted   path";
        final String message = "some message";
        InvalidPathEntry invalid = new InvalidPathEntry(path, message);
        assertEquals(invalid.toString(), path);
    }

}
