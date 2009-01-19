package org.kalisen.classpathdoctor;

public class EmptyPathEntry implements PathEntry {

	public static final EmptyPathEntry INSTANCE = new EmptyPathEntry();
	
	private EmptyPathEntry() {
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
