package org.kalisen.common;

public class DefaultErrorHandler implements ErrorHandler {

	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	public void handleError(String errorMessage) {
		System.err.println("Error" + errorMessage);
	}
}
