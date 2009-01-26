package org.kalisen.classpathdoctor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassPath {
	private List<PathEntry> entries = null;
	private ClassPathFormatter formatter = null;

	public ClassPath() {
		setEntries(new ArrayList<PathEntry>());
	}

	public ClassPath(List<PathEntry> entries) {
		setEntries(entries);
	}

	public List<PathEntry> getEntries() {
		return new ArrayList<PathEntry>(this.entries);
	}

	public void setEntries(List<? extends PathEntry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.entries = new ArrayList<PathEntry>(entries);
	}

	public void addEntry(PathEntry pathEntry) {
		if (pathEntry == null) {
			throw new IllegalArgumentException(ResourceBundle.getBundle(
					"UsersMessages").getString("null.is.not.a.valid.argument"));
		}
		this.entries.add(pathEntry);
	}

	@Override
	public String toString() {
		return getFormatter().format(this);
	}

	public ClassPathFormatter getFormatter() {
		if (this.formatter == null) {
			this.formatter = new DefaultClassPathFormatter();
		}
		return this.formatter;
	}

	public void setFormatter(ClassPathFormatter formatter) {
		if (formatter == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.formatter = formatter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		int entriesCount = this.entries.size();
		ClassPath other = (ClassPath) obj;
		if (entriesCount != other.entries.size()) {
			return false;
		}
		for (int i = 0; i < entriesCount; i++) {
			if (!this.entries.get(i).equals(other.entries.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean equalsIgnoreEmptyEntries(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		ClassPath other = (ClassPath) obj;
		//identify smaller list
		List<PathEntry> smallestList = null;
		List<PathEntry> otherList = null;
		if (this.entries.size() < other.entries.size()) {
			smallestList = this.entries;
			otherList = other.entries;
		} else {
			smallestList = other.entries;
			otherList = this.entries;
		}
		//compare list elements ignoring EmptyPathEntry objects
		int i = 0;
		int j = 0;
		PathEntry entry1 = null;
		PathEntry entry2 = null;
		while (i < smallestList.size()) {
			entry1 = smallestList.get(i);
			if (entry1.equals(EmptyPathEntry.INSTANCE)) {
				i++;
			} else if (j < otherList.size()) {
				entry2 = otherList.get(j);
				if (!entry2.equals(EmptyPathEntry.INSTANCE)) {
					if (!entry1.equals(entry2)) {
						return false;
					}
					i++;
				}
				j++;
			} else {
				return false;
			}
		}
		while (j < otherList.size()) {
			if (!otherList.get(j).equals(EmptyPathEntry.INSTANCE)) {
				return false;
			}
			j++;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public void removeEntry(PathEntry pathEntry) {
		if (pathEntry == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.entries.remove(pathEntry);
	}

}
