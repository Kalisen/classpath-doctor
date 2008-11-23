
package org.kalisen.classpathdoctor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class PathResolver {

    private VariableResolver varResolver = null;
	private String fileSeparator = null;

    public PathResolver() {
        // default constructor
    }

    public PathResolver(String fileSeparator) {
        this.fileSeparator = fileSeparator;
    }

    public PathEntry resolve(String path) {
        PathEntry result = null;
        if (path == null) {
            result = new InvalidPathEntry(path, ResourceBundle.getBundle("UsersMessages")
                                          .getString("null.is.not.a.valid.path"));
        } else {
            String expandedPath = null;
            expandedPath = getVariableResolver().resolve(path);
            try {
                URL url = new URL(expandedPath);
                result = new URLPath(url);
            } catch (MalformedURLException e) {
                File f = new File(expandedPath);
                if (f.isDirectory()) {
                    result = new DirectoryPath(f, getFileSeparator());
                } else if (f.isFile()) {
                    result = new JarPath(f);
                } else {
                    result = new InvalidPathEntry(expandedPath, ResourceBundle.getBundle("UsersMessages")
                                                  .getString("entry.doesnt.exist"));
                }
            }
        }
        return result;
    }

    public VariableResolver getVariableResolver() {
        if (this.varResolver == null) {
            this.varResolver = buildVariableResolver();
        }
        return this.varResolver;
    }

    private VariableResolver buildVariableResolver() {
        VariableResolver resolver = null;
        String pathSeparator = System.getProperty("path.separator");
        if (":".equals(pathSeparator)) {
            resolver = new UnixVariableResolver();
        } else {
            resolver = new WindowsVariableResolver();
        }
        return resolver;
    }

    public void setVariableResolver(VariableResolver varResolver) {
        if (varResolver == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.varResolver = varResolver;
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

}
