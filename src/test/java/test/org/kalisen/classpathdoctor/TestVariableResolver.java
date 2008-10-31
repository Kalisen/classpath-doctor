
package test.org.kalisen.classpathdoctor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import mockit.Mockit;

import org.kalisen.classpathdoctor.Environment;
import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestVariableResolver {

    private static final String VAR_NAME = "MY_VARIABLE";
    private static final String VAR_VALUE = "MY_VALUE";

    @Test
    public void resolve() {
        AbstractVariableResolver r = new AbstractVariableResolver();
        Mockit.redefineMethods(Environment.class, MockEnvironment.class);
        String value = r.resolve(VAR_NAME);
        assertEquals(value, VAR_VALUE);
    }

    @Test
    public void testEnvironmentSetterAndGetter() {
        AbstractVariableResolver r = new AbstractVariableResolver();
        Environment env = new Environment();
        r.setEnvironment(env);
        assertSame(r.getEnvironment(), env);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void passingNullToSetEnvironmentShouldThrowAnInvalidArgumentException() {
        AbstractVariableResolver r = new AbstractVariableResolver();
        r.setEnvironment(null);
    }

    @AfterTest
    public void tearDown() {
        Mockit.assertExpectations();
        Mockit.tearDownMocks();
    }

    public static class MockEnvironment {
        public String getValue(String variable) {
            if (VAR_NAME.equals(variable)) {
                return VAR_VALUE;
            } else {
                return null;
            }
        }
    }

}
