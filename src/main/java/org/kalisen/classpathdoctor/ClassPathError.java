package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;

public class ClassPathError {

	private PathEntry entry = null;

	public ClassPathError(PathEntry entry) {
		this.entry = entry;
	}

	public String getResourceName() {
		return (this.entry == null) ? "null" : this.entry.getPath();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(
				ResourceBundle.getBundle("UsersMessages").getString(
						"null.is.not.a.valid.argument")).append(
				getResourceName());
		return result.toString();
	}
}
