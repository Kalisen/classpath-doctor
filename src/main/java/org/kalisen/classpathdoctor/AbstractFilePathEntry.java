
package org.kalisen.classpathdoctor;

import java.io.File;
import java.util.ResourceBundle;

public abstract class AbstractFilePathEntry implements PathEntry {

    private Version version = new DefaultVersion();
    private File file = null;

    public AbstractFilePathEntry(File f) {
        setFile(f);
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File f) {
        if (f == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.file = f;
    }

    public String getPath() {
        return this.file.getPath();
    }

    public boolean exists() {
        return getFile().exists();
    }

    public Version getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return getPath();
    }

}