
package org.kalisen.classpathdoctor;

public class InvalidPathEntry implements PathEntry {

    private Version version = new DefaultVersion();
    private String path = null;
    private String message = null;

    public InvalidPathEntry(String path) {
        this.path = path;
    }

    public InvalidPathEntry(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public String getPath() {
        return this.path == null
                                ? "null" : this.path;
    }

    public Version getVersion() {
        return this.version;
    }

    public boolean exists() {
        return false;
    }

    public String toString() {
        if (this.message == null) {
            return getPath();
        } else {
            return new StringBuilder(this.message).append("(")
                    .append(getPath())
                    .append(")")
                    .toString();
        }
    }

}
