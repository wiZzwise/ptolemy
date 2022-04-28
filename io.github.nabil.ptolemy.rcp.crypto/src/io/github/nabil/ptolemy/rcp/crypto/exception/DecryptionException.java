package io.github.nabil.ptolemy.rcp.crypto.exception;

public class DecryptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public DecryptionException() {
		super();
	}

	public DecryptionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DecryptionException(String message) {
		super(message);
	}
}
