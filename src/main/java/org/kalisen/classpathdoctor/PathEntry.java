package org.kalisen.classpathdoctor;

public interface PathEntry extends PathElement {

	String getPath();

	boolean exists();

	Version getVersion();
}
