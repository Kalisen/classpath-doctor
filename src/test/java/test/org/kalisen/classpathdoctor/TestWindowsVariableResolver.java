
package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.WindowsVariableResolver;


public class TestWindowsVariableResolver extends AbstractTestVariableResolver {

    private static final String VAR_REFERENCE = "%MY_VARIABLE%";
    private static final String WINDOWS_PATH_SEPARATOR = ";";

    @Override
    protected String getPathSeparator() {
        return WINDOWS_PATH_SEPARATOR;
    }

    @Override
    protected String getVariableReference() {
        return VAR_REFERENCE;
    }

    @Override
    protected AbstractVariableResolver getTestedVariableResolver() {
        return new WindowsVariableResolver();
    }

}
