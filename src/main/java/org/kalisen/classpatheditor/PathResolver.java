
package org.kalisen.classpatheditor;

public class PathResolver {

    private VariableResolver varResolver = null;

    public PathResolver() {
        // default constructor
    }

    public PathEntry resolve(String path) {
        PathEntry result = null;
        return result;
    }

    public VariableResolver getVariableResolver() {
        if (this.varResolver == null) {
            this.varResolver = new VariableResolver();
        }
        return this.varResolver;
    }

    public void setVariableResolver(VariableResolver varResolver) {
        if (varResolver == null) {
            throw new IllegalArgumentException("Null is not a valid argument");
        }
        this.varResolver = varResolver;
    }

}
