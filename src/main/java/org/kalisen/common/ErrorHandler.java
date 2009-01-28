package org.kalisen.common;

public interface ErrorHandler {
	void handleError(Throwable t);

	void handleError(String errorMessage);
}
