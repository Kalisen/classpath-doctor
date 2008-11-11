
package org.kalisen.classpathdoctor;

import java.util.ArrayList;
import java.util.List;

public class ClassPath {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(this.entries.isEmpty()) {
            result.append("Classpath is empty");
        } else {
            result.append("Classpath Entries List:").append(LINE_SEPARATOR);
            for (PathEntry entry : this.entries) {
                result.append(entry.toString()).append(LINE_SEPARATOR);
            }
        }
        return result.toString();
    }
}
