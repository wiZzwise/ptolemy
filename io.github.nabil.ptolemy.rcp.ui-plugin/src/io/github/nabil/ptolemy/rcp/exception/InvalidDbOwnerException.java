package io.github.nabil.ptolemy.rcp.exception;

public class InvalidDbOwnerException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2773103634169239024L;

	public InvalidDbOwnerException() {
		super("Invalid Owner Credentials!");
	}

}
