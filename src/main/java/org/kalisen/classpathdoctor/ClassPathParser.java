
package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;


public class ClassPathParser {

    private String pathSeparator = null;
    private PathResolver pathResolver = null;

    public ClassPathParser() {
        // default constructor
    }

    public ClassPathParser(String pathSeparator) {
        setPathSeparator(pathSeparator);
    }

    public ClassPath parse(String stringClassPath) {
        ClassPath result = new ClassPath();
        if (stringClassPath != null && stringClassPath.trim().length() > 0) {
            String[] bits = stringClassPath.split(getPathSeparator());
            for (int i = 0; i < bits.length; i++) {
                result.addEntry(getPathResolver().resolve(bits[i]));
            }
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
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.pathSeparator = pathSeparator;
    }

    public PathResolver getPathResolver() {
        if (this.pathResolver == null) {
            this.pathResolver = new PathResolver();
        }
        return this.pathResolver;
    }

    public void setPathResolver(PathResolver pathResolver) {
        if (pathResolver == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.pathResolver = pathResolver;
    }
    
    

}
