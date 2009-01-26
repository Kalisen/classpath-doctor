package org.kalisen.classpathdoctor.adapter;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.ClassPathParser;
import org.kalisen.classpathdoctor.PathEntry;
import org.kalisen.common.adapter.AbstractAdapter;

public class DefaultClassPathAdapter extends AbstractAdapter implements
		ClassPathAdapter {

	private ClassPathParser parser = null;
	private ClassPath currentClasspath = null;
	private String currentClasspathAsText = null;

	public DefaultClassPathAdapter() {
		this.parser = new ClassPathParser();
		this.currentClasspath = new ClassPath();
		this.currentClasspathAsText = "";
	}

	public void setClassPathAsText(String text) {
		if (text == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (!this.currentClasspathAsText.equals(text)) {
			this.currentClasspathAsText = text;
			ClassPath newClasspath = this.parser.parse(text);
			if (!this.currentClasspath.equalsIgnoreEmptyEntries(newClasspath)) {
				this.currentClasspath = newClasspath;
				getNotifier().setChanged();
				getNotifier().notifyObservers(this.currentClasspath);
			}
		}
	}

	public String getClassPathAsText() {
		return this.currentClasspathAsText;
	}

	public void setClassPath(ClassPath cp) {
		if (cp == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (!this.currentClasspath.equalsIgnoreEmptyEntries(cp)) {
			this.currentClasspath = cp;
			this.currentClasspathAsText = cp.toString();
			getNotifier().setChanged();
			getNotifier().notifyObservers(this.currentClasspath);
		}
	}

	public ClassPath getClassPath() {
		return this.currentClasspath;
	}

	public void addEntry(String path) {
		if (path == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		PathEntry pathEntry = this.parser.getPathResolver().resolve(path);
		if (this.currentClasspathAsText.length() > 0) {
			this.currentClasspathAsText += this.parser
					.getPathSeparatorAsString();
		}
		this.currentClasspathAsText += path;
		this.currentClasspath.addEntry(pathEntry);
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}

	public void removeEntry(String entryPath) {
		if (entryPath == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (this.currentClasspathAsText.length() < entryPath.length()) {
			throw new IllegalArgumentException("Entry " + entryPath
					+ " doesn't exist in current classpath");
		}
		PathEntry pathEntry = this.parser.getPathResolver().resolve(entryPath);
		removeFromTextClassPath(entryPath);
		this.currentClasspath.removeEntry(pathEntry);
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}

	protected void removeFromTextClassPath(String entryPath) {
		StringBuffer temp = new StringBuffer(this.currentClasspathAsText);
		int entryIndex = temp.indexOf(entryPath);
		temp.delete(entryIndex, entryIndex + entryPath.length());
		final String SEPARATOR = this.parser.getPathSeparatorAsString();
		if (temp.indexOf(SEPARATOR) > -1) {
			int lastSeparatorIndex = temp.lastIndexOf(SEPARATOR);
			if (lastSeparatorIndex == temp.length() - SEPARATOR.length()) {
				// if there's a leftover separator at the end
				temp.delete(lastSeparatorIndex, temp.length());
			} else if (temp.substring(entryIndex, entryIndex + SEPARATOR.length()).equals(SEPARATOR)) {
				// if there's a leftover separator at the beginning or in the middle
				temp.delete(entryIndex, entryIndex + SEPARATOR.length());
			}
		}
		this.currentClasspathAsText = temp.toString();
	}

}
