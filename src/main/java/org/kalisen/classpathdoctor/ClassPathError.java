
package org.kalisen.classpathdoctor;

public class ClassPathError {

    private PathEntry entry = null;

    public ClassPathError(PathEntry entry) {
        this.entry = entry;
    }

    public String getResourceName() {
        return (this.entry == null)
                                   ? "null" : this.entry.getPath();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("the following entry is invalid: ")
                .append(getResourceName());
        return result.toString();
    }
}
