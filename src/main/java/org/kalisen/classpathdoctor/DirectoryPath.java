package org.kalisen.classpathdoctor;

import java.io.File;

public class DirectoryPath extends AbstractFilePathEntry {

	public DirectoryPath(File f) {
		super(f);
	}

	public DirectoryPath(File f, String fileSeparator) {
		super(f);
		setFileSeparator(fileSeparator);
	}

	public DirectoryPath(String stringPath) {
		super(stringPath);
	}

	public DirectoryPath(String stringPath, String fileSeparator) {
		super(stringPath);
		setFileSeparator(fileSeparator);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((getPath() == null) ? 0 : removeTrailingPathSeparator(
						getPath()).hashCode());
		result = prime * result
				+ ((getVersion() == null) ? 0 : getVersion().hashCode());
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
		DirectoryPath other = (DirectoryPath) obj;
		if (!equalsIgnoreTrailingPathSeparator(getPath(), other.getPath())) {
			return false;
		}
		if (getVersion() == null) {
			if (other.getVersion() != null) {
				return false;
			}
		} else if (!getVersion().equals(other.getVersion())) {
			return false;
		}
		return true;
	}

	private boolean equalsIgnoreTrailingPathSeparator(String path, String other) {
		path = removeTrailingPathSeparator(path);
		other = removeTrailingPathSeparator(other);
		if (path == null) {
			if (other != null) {
				return false;
			}
		} else if (!path.equals(other)) {
			return false;
		}
		return true;
	}

	protected String removeTrailingPathSeparator(String dirPath) {
		if (dirPath != null && dirPath.length() > 1
				&& dirPath.endsWith(getFileSeparator())) {
			dirPath = dirPath.substring(0, dirPath.length() - 1);
		}
		return dirPath;
	}

}
