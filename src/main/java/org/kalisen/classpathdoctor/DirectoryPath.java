
package org.kalisen.classpathdoctor;

import java.io.File;

public class DirectoryPath extends AbstractFilePathEntry {

    public DirectoryPath(File f) {
        super(f);
    }

	public DirectoryPath(String stringPath) {
		super(stringPath);
	}

}
