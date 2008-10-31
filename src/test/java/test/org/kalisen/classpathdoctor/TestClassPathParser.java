
package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;
import java.util.Set;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.ClassPathParser;
import org.kalisen.classpathdoctor.PathEntry;
import org.testng.annotations.Test;

public class TestClassPathParser {

    private static final String PATH_SEPARATOR = System.getProperty("entries.separator");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @Test
    public void parsingEmptyClasspathShouldReturnEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        ClassPath path = parser.parse("");
        Set<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 0);
    }

    @Test
    public void parsingNullClasspathShouldReturnEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        ClassPath path = parser.parse(null);
        Set<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 0);
    }

    @Test
    public void parsingValidClasspathStringShouldReturnNonEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        String cpString = "entry1"
                + PATH_SEPARATOR + "entry2" + PATH_SEPARATOR + "entry3"
                + PATH_SEPARATOR + "entry4" + PATH_SEPARATOR;
        ClassPath path = parser.parse(cpString);
        Set<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 4);
        Iterator<PathEntry> iter = entries.iterator();
        assertEquals(iter.next().getPath(), "entry1");
        assertEquals(iter.next().getPath(), "entry2");
        assertEquals(iter.next().getPath(), "entry3");
        assertEquals(iter.next().getPath(), "entry4");
    }

    @Test
    public void parsingValidClasspathStringWithFileSeparatorShouldReturnNonEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        String cpString = "entry1"
                + PATH_SEPARATOR + "entry2" + PATH_SEPARATOR + "entry3"
                + FILE_SEPARATOR + "subentry3" + PATH_SEPARATOR + "entry4"
                + PATH_SEPARATOR;
        ClassPath path = parser.parse(cpString);
        Set<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 4);
        Iterator<PathEntry> iter = entries.iterator();
        assertEquals(iter.next().getPath(), "entry1");
        assertEquals(iter.next().getPath(), "entry2");
        assertEquals(iter.next().getPath(), "entry3"
                + FILE_SEPARATOR + "subentry3");
        assertEquals(iter.next().getPath(), "entry4");
    }
}
