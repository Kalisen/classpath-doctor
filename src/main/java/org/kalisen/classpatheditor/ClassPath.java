
package org.kalisen.classpatheditor;

import java.util.HashSet;
import java.util.Set;

public class ClassPath {
    private Set<PathEntry> entries = null;

    public ClassPath() {
        // default constructor
    }

    public ClassPath(Set<PathEntry> entries) {
        setEntries(entries);
    }

    public Set<PathEntry> getEntries() {
        return new HashSet<PathEntry>(this.entries);
    }

    public void setEntries(Set<PathEntry> entries) {
        if (entries == null) {
            throw new IllegalArgumentException("null is not a valid argument");
        }
        this.entries = entries;
    }

    public void addEntry(PathEntry pathEntry) {
        // TODO Auto-generated method stub
    }

}
