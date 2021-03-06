package org.kalisen.classpathdoctor;

import java.io.Serializable;

public class EmptyPathEntry implements PathEntry, Serializable {

	public static final EmptyPathEntry INSTANCE = new EmptyPathEntry();

	protected EmptyPathEntry() {
		// private constructor for singleton pattern
	}

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
