
package test.org.kalisen.classpatheditor;

import static org.testng.Assert.assertEquals;
import mockit.Mockit;

import org.kalisen.classpatheditor.Environment;
import org.kalisen.classpatheditor.VariableResolver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class TestPathResolver {

    private static final String VAR_NAME = "MY_VARIABLE";
    private static final String VAR_VALUE = "MY_VALUE";

    @Test
    public void resolve() {
        VariableResolver r = new VariableResolver();
        Mockit.redefineMethods(Environment.class, MockEnvironment.class);
        String value = r.resolve(VAR_NAME);
        assertEquals(value, VAR_VALUE);
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
