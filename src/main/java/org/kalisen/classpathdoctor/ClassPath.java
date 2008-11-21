
package org.kalisen.classpathdoctor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClassPath {
    private List<PathEntry> entries = null;
    private ClassPathFormatter formatter = null;

    public ClassPath() {
        this.entries = new ArrayList<PathEntry>();
    }

    public ClassPath(ArrayList<PathEntry> entries) {
        setEntries(entries);
    }

    public List<PathEntry> getEntries() {
        return new ArrayList<PathEntry>(this.entries);
    }

    public void setEntries(List<PathEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.entries = new ArrayList<PathEntry>(entries);
    }

    public void addEntry(PathEntry pathEntry) {
        if (pathEntry == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
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

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
    
    
}
