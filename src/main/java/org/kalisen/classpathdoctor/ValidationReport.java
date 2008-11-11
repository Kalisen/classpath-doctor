
package org.kalisen.classpathdoctor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ValidationReport {
    
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private List<ClassPathError> errors = null;

    public ValidationReport() {
        this.errors = new ArrayList<ClassPathError>();
    }
    
    public boolean isOk() {
        return this.errors.isEmpty();
    }

    public List<ClassPathError> getErrors() {
        return new ArrayList<ClassPathError>(this.errors);
    }

    public void addError(ClassPathError classPathError) {
        if (classPathError == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("UsersMessages")
                                               .getString("null.is.not.a.valid.argument"));
        }
        this.errors.add(classPathError);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(this.errors.isEmpty()) {
            result.append(ResourceBundle.getBundle("UsersMessages")
                          .getString("no.error.were.found"));
        } else {
            result.append(ResourceBundle.getBundle("UsersMessages")
                          .getString("errors.entries.list")).append(LINE_SEPARATOR);
            for (ClassPathError error : this.errors) {
                result.append(error.toString()).append(LINE_SEPARATOR);
            }
        }
        return result.toString();
    }
}
