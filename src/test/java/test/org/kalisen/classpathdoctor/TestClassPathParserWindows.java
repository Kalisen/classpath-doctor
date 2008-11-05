package test.org.kalisen.classpathdoctor;

import org.kalisen.classpathdoctor.AbstractVariableResolver;
import org.kalisen.classpathdoctor.WindowsVariableResolver;
import org.testng.annotations.Test;

@Test
public class TestClassPathParserWindows extends AbstractTestClassPathParser {

    @Override
    protected String getTestedPathSeparator() {
        return ";";
    }

    @Override
    protected String getTestedFileSeparator() {
        return "\\";
    }

    @Override
    protected String getTestedVariableReference() {
        return "%MY_VARIABLE%";
    }

    @Override
    protected AbstractVariableResolver getTestedVariableResolver() {
        return new WindowsVariableResolver();
    }
    
    

}
