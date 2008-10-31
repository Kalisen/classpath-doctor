
package org.kalisen.classpathdoctor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class PathResolver {

    private VariableResolver varResolver = null;

    public PathResolver() {
        // default constructor
    }

    public PathEntry resolve(String path) {
        PathEntry result = null;
        if (path == null) {
            result = new InvalidPathEntry(path, "null is not a valid path");
        } else {
            String expandedPath = getVariableResolver().resolve(path);
            try {
                URL url = new URL(expandedPath);
                result = new URLPath(url);
            } catch (MalformedURLException e) {
                File f = new File(expandedPath);
                if (f.isDirectory()) {
                    result = new DirectoryPath(f);
                } else if (f.isFile()) {
                    result = new JarPath(f);
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
            throw new IllegalArgumentException("Null is not a valid argument");
        }
        this.varResolver = varResolver;
    }

}
