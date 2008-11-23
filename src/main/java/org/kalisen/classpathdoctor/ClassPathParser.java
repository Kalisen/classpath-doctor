
package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;


public class ClassPathParser {

    private String pathSeparator = null;
    private String fileSeparator = null;
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
            	if (bits[i] != null && bits[i].length() > 0) {
            		result.addEntry(getPathResolver().resolve(bits[i]));
            	}
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

    public String getFileSeparator() {
    	if (this.fileSeparator == null) {
    		this.fileSeparator = System.getProperty("file.separator");
    	}
		return this.fileSeparator;
	}

	public void setFileSeparator(String fileSeparator) {
		if (fileSeparator == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.fileSeparator = fileSeparator;
	}

	public PathResolver getPathResolver() {
        if (this.pathResolver == null) {
            this.pathResolver = new PathResolver(getFileSeparator());
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
