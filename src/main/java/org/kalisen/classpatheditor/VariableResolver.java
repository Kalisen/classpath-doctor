
package org.kalisen.classpatheditor;

public class VariableResolver {

    private Environment env = null;

    public VariableResolver() {
        // default constructor
    }

    public String resolve(String variable) {
        return getEnvironment().getValue(variable);
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

}
