
package org.kalisen.classpathdoctor;

public interface PathEntry {

    String getPath();
    
    boolean exists();
    
    Version getVersion();
}
