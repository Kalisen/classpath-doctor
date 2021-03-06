package org.kalisen.classpathdoctor;

import java.io.File;
import java.io.Serializable;
import java.util.ResourceBundle;

public abstract class AbstractFilePathEntry implements PathEntry, Serializable {

	private Version version = new DefaultVersion();
	private String fileSeparator = null;
	private File file = null;
	private String path = null;

	public AbstractFilePathEntry(File f) {
		setFile(f);
	}

	public AbstractFilePathEntry(String filePath) {
		setPath(filePath);
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File f) {
		if (f == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.file = f;
		this.path = this.file.getPath();
	}

	public void setPath(String path) {
		if (path == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.path = path;
		this.file = new File(path);
	}

	public String getPath() {
		return this.path;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.path == null) ? 0 : this.path.hashCode());
		result = prime * result
				+ ((this.version == null) ? 0 : this.version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractFilePathEntry other = (AbstractFilePathEntry) obj;
		if (this.path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!this.path.equals(other.path)) {
			return false;
		}
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
			return false;
		}
		return true;
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