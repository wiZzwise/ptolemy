package io.github.nabil.ptolemy.rcp.crypto.exception;

public class EncryptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public EncryptionException() {
		super();
	}

	public EncryptionException(String message) {
		super(message);
	}

	public EncryptionException(String message, Throwable cause) {
		super(message, cause);
	}
}
