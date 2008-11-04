
package test.org.kalisen.classpathdoctor;


public class TestWindowsVariableResolver extends AbstractTestVariableResolver {

    private static final String VAR_NAME = "MY_VARIABLE";
    private static final String VAR_REFERENCE = "%MY_VARIABLE%";
    private static final String WINDOWS_PATH_SEPARATOR = ";";

    @Override
    protected String getPathSeparator() {
        return WINDOWS_PATH_SEPARATOR;
    }

    @Override
    protected String getVariableName() {
        return VAR_NAME;
    }

    @Override
    protected String getVariableReference() {
        return VAR_REFERENCE;
    }

    @Override
    protected Object getMockEnvironment() {
        return new MockEnvironment();
    }

    public static class MockEnvironment {

        public String getValue(String variable) {
            if (VAR_NAME.equals(variable)) {
                return AbstractTestVariableResolver.VAR_VALUE;
            } else {
                return null;
            }
        }
    }

}
