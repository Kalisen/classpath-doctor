
package org.kalisen.classpathdoctor;

import java.util.ArrayList;
import java.util.List;

public class ClassPath {
    private List<PathEntry> entries = null;

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
            throw new IllegalArgumentException("null is not a valid argument");
        }
        this.entries = new ArrayList<PathEntry>(entries);
    }

    public void addEntry(PathEntry pathEntry) {
        if (pathEntry == null) {
            throw new IllegalArgumentException("null is not a valid argument");
        }
        this.entries.add(pathEntry);
    }

}
