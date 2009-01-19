package org.kalisen.classpathdoctor;

import java.util.List;
import java.util.ResourceBundle;

public class DisplayClassPathFormatter implements ClassPathFormatter {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	public String format(ClassPath cp) {
		StringBuilder builder = format(cp, new StringBuilder());
		return builder.toString();
	}

	public StringBuilder format(ClassPath cp, StringBuilder builder) {
		if (builder == null) {
			builder = new StringBuilder();
		}
		List<PathEntry> entries = cp.getEntries();
		if (entries.isEmpty()) {
			builder.append(ResourceBundle.getBundle("UsersMessages").getString(
					"classpath.is.empty"));
		} else {
			builder.append(
					ResourceBundle.getBundle("UsersMessages").getString(
							"classpath.entries.list")).append(LINE_SEPARATOR);
			for (PathEntry entry : entries) {
				builder.append(entry.toString()).append(LINE_SEPARATOR);
			}
		}
		return builder;
	}
}
