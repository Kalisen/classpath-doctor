
package org.kalisen.classpathdoctor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractVariableResolver implements VariableResolver {

    private Environment env = null;

    public AbstractVariableResolver() {
        // default constructor
    }

    public String resolve(String path) {
        String result = null;
        Pattern p = getVariablePattern();
        Matcher m = p.matcher(path);
        String var = null;
        String varValue = null;
        while (m.find()) {
            var = m.group();
            varValue = resolve(getEnvironment().getValue(var));
            result = path.replaceAll(var, varValue);
        }
        return result;
    }

    public Environment getEnvironment() {
        if (this.env == null) {
            this.env = new Environment();
        }
        return this.env;
    }

    public void setEnvironment(Environment env) {
        if (env == null) {
            throw new IllegalArgumentException("Null is not a valid argument");
        }
        this.env = env;
    }
    
    protected abstract Pattern getVariablePattern();

}
