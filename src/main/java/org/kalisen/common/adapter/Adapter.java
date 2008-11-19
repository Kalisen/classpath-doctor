package org.kalisen.common.adapter;

import java.util.Observer;

public interface Adapter {	
	void addListener(Observer o);
	
	void removeListener(Observer o);
}
