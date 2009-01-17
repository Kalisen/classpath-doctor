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
			ClassPath newClasspath = parser.parse(text);
			if (!this.currentClasspath.equals(newClasspath)) {
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
		this.currentClasspath = cp;
		this.currentClasspathAsText = cp.toString();
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}
	
	public ClassPath getClassPath() {
		return this.currentClasspath;
	}
	
	public void addEntry(String entryPath) {
		if (entryPath == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		PathEntry pathEntry = this.parser.getPathResolver().resolve(
				entryPath);
		if (this.currentClasspathAsText.length() > 0) {
			this.currentClasspathAsText += this.parser
					.getPathSeparatorAsString();
		}
		this.currentClasspathAsText += entryPath;
		this.currentClasspath.addEntry(pathEntry);
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}

	public void removeEntry(String entryPath) {
		// TODO Auto-generated method stub

	}

}
