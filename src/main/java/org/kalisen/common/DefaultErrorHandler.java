package org.kalisen.common;

public class DefaultErrorHandler implements ErrorHandler {

	public void handleError(Throwable t) {
		t.printStackTrace();
	}

}
