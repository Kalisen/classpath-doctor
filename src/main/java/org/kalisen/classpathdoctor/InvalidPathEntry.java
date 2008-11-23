
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
    
    public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean exists() {
        return false;
    }

    public String toString() {
        return getPath();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvalidPathEntry other = (InvalidPathEntry) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

    
}
