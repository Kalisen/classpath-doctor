package org.kalisen.classpathdoctor;

import java.util.ResourceBundle;

public class DefaultPathEntryFormatter implements PathEntryFormatter<PathEntry> {

	public String format(PathEntry entry) {
		return format(entry, new StringBuilder()).toString();
	}

	public StringBuilder format(PathEntry entry, StringBuilder builder) {
		StringBuilder result = new StringBuilder();
		if (entry.exists()) {
			result.append(entry.getPath());
		} else {
			result.append(
					ResourceBundle.getBundle("user.messages").getString(
							"entry.doesnt.exist")).append("(").append(
					entry.getPath()).append(")").toString();
		}
		return result;
	}

}
