package org.kalisen.classpathdoctor;

public interface PathEntryFormatter<T extends PathEntry> {
	String format(T entry);

	StringBuilder format(T entry, StringBuilder builder);
}
