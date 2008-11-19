package org.kalisen.common.adapter;

import java.util.Observable;
import java.util.Observer;

public abstract class AbstractAdapter implements Adapter {

	private MyObservable notifier = null;

	public AbstractAdapter() {
		setNotifier(new MyObservable());
	}
	
	public void addListener(Observer o) {
		if (o == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.notifier.addObserver(o);
	}
	
	public void removeListener(Observer o) {
		if (o == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.notifier.deleteObserver(o);
	}

	protected MyObservable getNotifier() {
		return this.notifier;
	}

	protected void setNotifier(MyObservable notifier) {
		if (notifier == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.notifier = notifier;
	}
	

	protected class MyObservable extends Observable {

		@Override
		public synchronized void setChanged() {
			super.setChanged();
		}
		
	}
}
