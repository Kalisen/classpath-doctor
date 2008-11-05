
package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import mockit.Mockit;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.Environment;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

@Test
public abstract class AbstractTestVariableResolver {

    private static final String VAR_NAME = "MY_VARIABLE";
    protected static final String VAR_VALUE = "MY_VALUE";

    @Test
    public void resolveVariable() {
        AbstractVariableResolver r = getTestedVariableResolver();
        Mockit.redefineMethods(Environment.class, MockEnvironment.class);
        String value = r.resolve(getVariableReference());
        assertEquals(value, VAR_VALUE);
    }

    @Test
    public void resolveVariableWithinAPath() {
        AbstractVariableResolver r = getTestedVariableResolver();
        Mockit.redefineMethods(Environment.class, MockEnvironment.class);
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
        AbstractVariableResolver r = getTestedVariableResolver();
        Environment env = new Environment();
        r.setEnvironment(env);
        assertSame(r.getEnvironment(), env);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void passingNullToSetEnvironmentShouldThrowAnInvalidArgumentException() {
        AbstractVariableResolver r = getTestedVariableResolver();
        r.setEnvironment(null);
    }

    @AfterTest
    public void tearDown() {
        Mockit.assertExpectations();
        Mockit.tearDownMocks();
    }

    protected abstract String getVariableReference();

    protected abstract String getPathSeparator();

    public static class MockEnvironment {
        public String getValue(String variable) {
            if (VAR_NAME.equals(variable)) {
                return AbstractTestVariableResolver.VAR_VALUE;
            } else {
                return null;
            }
        }
    }


    protected abstract AbstractVariableResolver getTestedVariableResolver();
}
