package org.kalisen.classpathdoctor.gui;

import javax.swing.AbstractListModel;

import org.kalisen.classpathdoctor.ClassPath;

public class ClassPathListModel extends AbstractListModel {

	private ClassPath classPath = null;
	
	public ClassPathListModel() {
		super();
		this.classPath = new ClassPath();
	}
	
	public ClassPath getClassPath() {
		return this.classPath;
	}

	public void setClassPath(ClassPath newClassPath) throws IllegalArgumentException {
		if (newClassPath == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (!this.classPath.equals(newClassPath)) {
			this.classPath = newClassPath;
			fireContentsChanged(this, 0, getSize() - 1);
		}
	}

	public Object getElementAt(int index) {
		return this.classPath.getEntries().get(index);
	}

	public int getSize() {
		return this.classPath.getEntries().size();
	}

	
}
