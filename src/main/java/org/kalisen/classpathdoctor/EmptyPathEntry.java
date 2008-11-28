package org.kalisen.classpathdoctor;

public class EmptyPathEntry implements PathEntry {

	public boolean exists() {
		return false;
	}

	public String getPath() {
		return "";
	}

	public Version getVersion() {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass());
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return getPath();
	}
}
