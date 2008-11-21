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

	public void setClassPath(String text) {
		System.out.println("call to setClassPath with text = " + text);
		System.out.println("current classpath = '" + this.currentClasspathAsText + "'");
		if (text != null && !text.equals(this.currentClasspathAsText)) {
			this.currentClasspathAsText = text;
			this.currentClasspath = parser.parse(text);
			getNotifier().setChanged();
			getNotifier().notifyObservers(this.currentClasspath);
		}
	}

	public void addEntry(String entryPath) {
		System.out.println("call to addEntry with text = " + entryPath);
		if (entryPath != null && entryPath.length() > 0) {
			PathEntry pathEntry = this.parser.getPathResolver().resolve(
					entryPath);
			if (this.currentClasspathAsText.length() > 0) {
				this.currentClasspathAsText += this.parser.getPathSeparator();
			}
			this.currentClasspathAsText += entryPath;
			this.currentClasspath.addEntry(pathEntry);
			getNotifier().setChanged();
			getNotifier().notifyObservers(this.currentClasspath);
		}
	}

	public void removeEntry(String entryPath) {
		// TODO Auto-generated method stub

	}

}
