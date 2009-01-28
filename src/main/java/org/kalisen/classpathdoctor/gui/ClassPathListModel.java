package org.kalisen.classpathdoctor.gui;

import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.classpathdoctor.PathEntry;

public class ClassPathListModel extends AbstractListModel {

	private ClassPath classPath = null;

	public ClassPathListModel() {
		super();
		this.classPath = new ClassPath();
	}

	public ClassPath getClassPath() {
		return new ClassPath(this.classPath);
	}

	public void setClassPath(ClassPath newClassPath)
			throws IllegalArgumentException {
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

	public void removeElementAt(int index) {
		List<PathEntry> entries = this.classPath.getEntries();
		entries.remove(index);
		this.classPath.setEntries(entries);
		fireIntervalRemoved(this, index, index);
	}

	public void removeElementsAt(int[] indices) {
		if (indices.length > 0) {
			List<PathEntry> entries = this.classPath.getEntries();
			for (int i = 0; i < indices.length; i++) {
				entries.remove(indices[i]);
			}
			this.classPath.setEntries(entries);
			fireIntervalRemoved(this, indices[0], indices[indices.length - 1]);
		}
	}

	public void insertElementAt(int index, PathEntry entry) {
		List<PathEntry> entries = this.classPath.getEntries();
		entries.add(index, entry);
		this.classPath.setEntries(entries);
		fireIntervalAdded(this, index, index);
	}

	public void insertElementsAt(int index, PathEntry[] entries) {
		List<PathEntry> newEntries = this.classPath.getEntries();
		newEntries.addAll(index, Arrays.asList(entries));
		this.classPath.setEntries(newEntries);
		fireIntervalAdded(this, index, index + entries.length);
	}
}
