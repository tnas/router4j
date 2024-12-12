package io.github.tnas.router4j.exception;

public class RouterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RouterException() {
	}

	public RouterException(String message) {
		super(message);
	}

	public RouterException(Throwable cause) {
		super(cause);
	}

	public RouterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RouterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
