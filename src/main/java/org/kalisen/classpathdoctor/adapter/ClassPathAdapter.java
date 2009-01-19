package org.kalisen.classpathdoctor.adapter;

import org.kalisen.classpathdoctor.ClassPath;
import org.kalisen.common.adapter.Adapter;

public interface ClassPathAdapter extends Adapter {

	void setClassPath(ClassPath cp);

	ClassPath getClassPath();

	void setClassPathAsText(String text);

	String getClassPathAsText();

	void addEntry(String entryPath);

	void removeEntry(String entryPath);

}
