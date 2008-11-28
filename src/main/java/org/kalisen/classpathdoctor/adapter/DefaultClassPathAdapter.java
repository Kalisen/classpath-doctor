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
		System.out.println("called setClasspath with text: " + text);
		System.out.println("with current Classpath set to: "
				+ this.currentClasspathAsText);
		ClassPath newClasspath = parser.parse(text);
		if (!this.currentClasspathAsText.equals(text)
				&& !newClasspath.equals(this.currentClasspath)) {
			this.currentClasspathAsText = text;
			this.currentClasspath = newClasspath;
			getNotifier().setChanged();
			getNotifier().notifyObservers(this.currentClasspath);
		}
	}

	public void addEntry(String entryPath) {
		if (entryPath != null && entryPath.length() > 0) {
			PathEntry pathEntry = this.parser.getPathResolver().resolve(
					entryPath);
			if (this.currentClasspathAsText.length() > 0) {
				this.currentClasspathAsText += this.parser.getPathSeparatorAsString();
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
