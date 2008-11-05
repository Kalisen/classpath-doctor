
package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Iterator;
import java.util.List;

import mockit.Mockit;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.ClassPathParser;
import org.kalisen.classpathdoctor.Environment;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.classpathdoctor.PathResolver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public abstract class AbstractTestClassPathParser {

    private static final String VAR_NAME = "MY_VARIABLE";
    private static final String VAR_VALUE = "MY_VALUE";


    @AfterTest
    public void tearDown() {
        Mockit.assertExpectations();
        Mockit.tearDownMocks();
    }

    @Test
    public void thePathSeparatorSetInTheConstructorShouldBeRetrievedThroughTheGetMethod() {
        final String pathSeparator = "%";
        ClassPathParser parser = new ClassPathParser(pathSeparator);
        assertEquals(parser.getPathSeparator(), pathSeparator);
    }
    
    @Test
    public void byDefaultThePathSeparatorShouldBeTheOneOfTheCurrentPlatform() {
        ClassPathParser parser = new ClassPathParser();
        assertEquals(parser.getPathSeparator(), System.getProperty("path.separator"));
    }
    
    @Test
    public void parsingEmptyClasspathShouldReturnEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        ClassPath path = parser.parse("");
        List<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 0);
    }

    @Test
    public void parsingNullClasspathShouldReturnEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser();
        ClassPath path = parser.parse(null);
        List<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 0);
    }

    @Test
    public void parsingValidClasspathStringShouldReturnNonEmptyClasspath() {
        ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
        String cpString = "entry1"
                + getTestedPathSeparator() + "entry2" + getTestedPathSeparator() + "entry3"
                + getTestedPathSeparator() + "entry4" + getTestedPathSeparator();
        ClassPath path = parser.parse(cpString);
        List<PathEntry> entries = path.getEntries();
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
        ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
        String cpString = "entry1"
                + getTestedPathSeparator() + "entry2" + getTestedPathSeparator() + "entry3"
                + getTestedFileSeparator() + "subentry3" + getTestedPathSeparator() + "entry4"
                + getTestedPathSeparator();
        ClassPath path = parser.parse(cpString);
        List<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 4);
        Iterator<PathEntry> iter = entries.iterator();
        assertEquals(iter.next().getPath(), "entry1");
        assertEquals(iter.next().getPath(), "entry2");
        assertEquals(iter.next().getPath(), "entry3"
                + getTestedFileSeparator() + "subentry3");
        assertEquals(iter.next().getPath(), "entry4");
    }

    @Test
    public void parsingValidClasspathStringUsingVariableShouldReturnNonEmptyClasspathAndSubstitueVariableValue() {
        Mockit.redefineMethods(Environment.class, MockEnvironment.class);
        ClassPathParser parser = new ClassPathParser(getTestedPathSeparator());
        PathResolver pathResolver = new PathResolver();
        pathResolver.setVariableResolver(getTestedVariableResolver());
        parser.setPathResolver(pathResolver);
        String cpString = "entry1"
                + getTestedPathSeparator() + getTestedVariableReference() + getTestedPathSeparator() + "entry3"
                + getTestedFileSeparator() + "subentry3" + getTestedPathSeparator() + "entry4"
                + getTestedPathSeparator();
        ClassPath path = parser.parse(cpString);
        List<PathEntry> entries = path.getEntries();
        assertNotNull(entries);
        assertEquals(entries.size(), 4);
        Iterator<PathEntry> iter = entries.iterator();
        assertEquals(iter.next().getPath(), "entry1");
        assertEquals(iter.next().getPath(), VAR_VALUE);
        assertEquals(iter.next().getPath(), "entry3"
                + getTestedFileSeparator() + "subentry3");
        assertEquals(iter.next().getPath(), "entry4");
    }

    protected abstract String getTestedPathSeparator();

    protected abstract String getTestedFileSeparator();

    protected abstract String getTestedVariableReference();

    protected abstract AbstractVariableResolver getTestedVariableResolver();

    public static class MockEnvironment {
        public String getValue(String variable) {
            if (VAR_NAME.equals(variable)) {
                return AbstractTestClassPathParser.VAR_VALUE;
            } else {
                return null;
            }
        }
    }

}
