package org.kalisen.classpathdoctor;

public class InvalidPathEntryFormatter implements
		PathEntryFormatter<InvalidPathEntry> {

	public String format(InvalidPathEntry entry) {
		return format(entry, new StringBuilder()).toString();
	}

	public StringBuilder format(InvalidPathEntry entry, StringBuilder builder) {
		StringBuilder result = new StringBuilder();
		if (entry.getMessage() == null) {
			result.append(entry.getPath());
		} else {
			result.append(entry.getMessage()).append("(").append(
					entry.getPath()).append(")").toString();
		}
		return result;
	}

}
