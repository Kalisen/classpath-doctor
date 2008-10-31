
package org.kalisen.classpathdoctor;

import java.io.File;

public class ClassPathParser {

    private String pathSeparator = null;
    private VariableResolver variableResolver = null;

    public ClassPathParser() {
        // default constructor
    }

    public ClassPathParser(String pathSeparator) {
        setPathSeparator(pathSeparator);
    }

    public ClassPath parse(String stringClassPath) {
        ClassPath result = new ClassPath();
        if (stringClassPath != null) {
            String[] bits = stringClassPath.split(getPathSeparator());
            for (int i = 0; i < bits.length; i++) {
                result.addEntry(buildPathEntry(bits[i]));
            }
        }
        return result;
    }

    private PathEntry buildPathEntry(String path) {
        PathEntry result = null;
        if (path == null) {
            result = new InvalidPathEntry(path);
        }
        File f = new File(path);
        if (f.isFile()) {
            result = new JarPath(f);
        } else if (f.isDirectory()) {
            result = new DirectoryPath(f);
        } else {
            result = new InvalidPathEntry(path);
        }
        return result;
    }

    public String getPathSeparator() {
        if (this.pathSeparator == null) {
            this.pathSeparator = System.getProperty("path.separator");
        }
        return this.pathSeparator;
    }

    public void setPathSeparator(String pathSeparator) {
        if (pathSeparator == null) {
            throw new IllegalArgumentException("null is not a valid argument");
        }
        this.pathSeparator = pathSeparator;
    }

    public VariableResolver getResolver() {
        if (this.variableResolver == null) {
            this.variableResolver = new VariableResolver();
        }
        return this.variableResolver;
    }

    public void setResolver(VariableResolver variableResolver) {
        if (variableResolver == null) {
            throw new IllegalArgumentException("Null is not a valid argument");
        }
        this.variableResolver = variableResolver;
    }

}
