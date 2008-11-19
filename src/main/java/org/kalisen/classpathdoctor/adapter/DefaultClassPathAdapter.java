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
	
	public void updateClassPath(String text) {
		this.currentClasspath = parser.parse(text);
		getNotifier().setChanged();
		getNotifier().notifyObservers(this.currentClasspath);
	}

}
