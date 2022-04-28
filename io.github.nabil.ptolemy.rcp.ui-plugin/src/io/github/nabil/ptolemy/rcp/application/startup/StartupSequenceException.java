package io.github.nabil.ptolemy.rcp.application.startup;

public class StartupSequenceException extends RuntimeException {

	private static final long serialVersionUID = 4535776472360609303L;

	public StartupSequenceException() {
		super();
	}

	public StartupSequenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public StartupSequenceException(String message) {
		super(message);
	}
}
