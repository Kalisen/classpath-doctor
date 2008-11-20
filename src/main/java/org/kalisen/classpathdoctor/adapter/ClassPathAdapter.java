package org.kalisen.classpathdoctor.adapter;

import org.kalisen.common.adapter.Adapter;

public interface ClassPathAdapter extends Adapter {

	void setClassPath(String text);

	void addEntry(String entryPath);

	void removeEntry(String entryPath);

}
