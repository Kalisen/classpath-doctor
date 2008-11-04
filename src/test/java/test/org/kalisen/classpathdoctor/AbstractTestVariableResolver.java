
package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import mockit.Mockit;

import org.kalisen.classpathdoctor.Environment;
import org.kalisen.classpathdoctor.WindowsVariableResolver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public abstract class AbstractTestVariableResolver {

    protected static final String VAR_VALUE = "MY_VALUE";

    @Test
    public void resolveVariable() {
        WindowsVariableResolver r = new WindowsVariableResolver();
        Mockit.redefineMethods(Environment.class, getMockEnvironment());
        String value = r.resolve(getVariableReference());
        assertEquals(value, VAR_VALUE);
    }

    @Test
    public void resolveVariableWithinAPath() {
        WindowsVariableResolver r = new WindowsVariableResolver();
        Mockit.redefineMethods(Environment.class, getMockEnvironment());
        String path = "some_path"
                + getPathSeparator() + getVariableReference() + getPathSeparator()
                + "some more path" + getPathSeparator();
        String expectedPath = "some_path"
                + getPathSeparator() + VAR_VALUE + getPathSeparator()
                + "some more path" + getPathSeparator();
        String result = r.resolve(path);
        assertEquals(result, expectedPath);
    }

    @Test
    public void testEnvironmentSetterAndGetter() {
        WindowsVariableResolver r = new WindowsVariableResolver();
        Environment env = new Environment();
        r.setEnvironment(env);
        assertSame(r.getEnvironment(), env);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void passingNullToSetEnvironmentShouldThrowAnInvalidArgumentException() {
        WindowsVariableResolver r = new WindowsVariableResolver();
        r.setEnvironment(null);
    }

    @AfterTest
    public void tearDown() {
        Mockit.assertExpectations();
        Mockit.tearDownMocks();
    }

    protected abstract String getVariableName();

    protected abstract String getVariableReference();

    protected abstract String getPathSeparator();

    protected abstract Object getMockEnvironment();
}
