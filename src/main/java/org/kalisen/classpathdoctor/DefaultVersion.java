package org.kalisen.classpathdoctor;

public class DefaultVersion implements Version {

	private int major = 0;
	private int minor = 0;
	private int patch = 0;

	public DefaultVersion() {
		super();
	}

	public DefaultVersion(int major, int minor, int patch) {
		super();
		setMajor(major);
		setMinor(minor);
		setPatch(patch);
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getMajor()).append(".").append(
				getMinor()).append(".").append(getPatch()).toString();
	}

	public int getMajor() {
		return this.major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return this.minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPatch() {
		return this.patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.major;
		result = prime * result + this.minor;
		result = prime * result + this.patch;
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
		DefaultVersion other = (DefaultVersion) obj;
		if (this.major != other.major) {
			return false;
		}
		if (this.minor != other.minor) {
			return false;
		}
		if (this.patch != other.patch) {
			return false;
		}
		return true;
	}

}
