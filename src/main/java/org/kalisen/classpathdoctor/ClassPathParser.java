package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;

public class ClassPathParser {

	private PathSeparator pathSeparator = null;
	private String fileSeparator = null;
	private PathResolver pathResolver = null;

	public ClassPathParser() {
		// default constructorSeparator
	}

	public ClassPathParser(String pathSeparator) {
		setPathSeparator(new PathSeparator(pathSeparator));
	}

	public ClassPath parse(String stringClassPath) {
		ClassPath result = new ClassPath();
		if (stringClassPath != null && stringClassPath.trim().length() > 0) {
			int trailingSepCount = stringClassPath.length();
			PathSeparator pathSeparator = getPathSeparator();
			String[] bits = stringClassPath.split(pathSeparator.toString());
			trailingSepCount -= (bits.length - 1)
					* pathSeparator.toString().length();
			PathEntry currentEntry = null;
			for (int i = 0; i < bits.length; i++) {
				if (bits[i] != null) {
					currentEntry = getPathResolver().resolve(bits[i]);
					result.addEntry(currentEntry);
					trailingSepCount -= bits[i].length();
				}
			}
			// adding trailing separators if any
			for (int i = 0; i < trailingSepCount; i++) {
				result.addEntry(EmptyPathEntry.INSTANCE);
			}
		}
		return result;
	}

	public PathSeparator getPathSeparator() {
		if (this.pathSeparator == null) {
			this.pathSeparator = new PathSeparator(System
					.getProperty("path.separator"));
		}
		return this.pathSeparator;
	}

	public String getPathSeparatorAsString() {
		return getPathSeparator().toString();
	}

	public void setPathSeparator(PathSeparator pathSeparator) {
		if (pathSeparator == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.pathSeparator = pathSeparator;
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

	public PathResolver getPathResolver() {
		if (this.pathResolver == null) {
			this.pathResolver = new PathResolver(getFileSeparator());
		}
		return this.pathResolver;
	}

	public void setPathResolver(PathResolver pathResolver) {
		if (pathResolver == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.pathResolver = pathResolver;
	}

}
