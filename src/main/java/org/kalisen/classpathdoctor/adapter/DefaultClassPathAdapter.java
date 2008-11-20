package org.kalisen.classpathdoctor.adapter;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.ClassPathParser;
import org.kalisen.common.adapter.AbstractAdapter;

public class DefaultClassPathAdapter extends AbstractAdapter implements
		ClassPathAdapter {
	
	private ClassPathParser parser = null;
	private ClassPath currentClasspath = null;

	public DefaultClassPathAdapter() {
	      this.parser = new ClassPathParser();
	}
	
	public void setClassPath(String text) {
		this.currentClasspath = parser.parse(text);
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}

	public void addEntry(String entryPath) {
		// TODO Auto-generated method stub
		
	}

	public void removeEntry(String entryPath) {
		// TODO Auto-generated method stub
		
	}
	
}
