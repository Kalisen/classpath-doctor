package org.kalisen.classpathdoctor;

import java.io.Serializable;


public class PathSeparator implements PathElement, Serializable {

	private String pathSeparator = null;

	public PathSeparator(String pathSeparator) {
		if (pathSeparator == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.pathSeparator = pathSeparator;
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
		return this.pathSeparator;
	}

}
