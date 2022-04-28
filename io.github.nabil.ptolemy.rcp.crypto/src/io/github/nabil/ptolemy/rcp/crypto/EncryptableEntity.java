package io.github.nabil.ptolemy.rcp.crypto;

public interface EncryptableEntity {
	boolean isCrypted();

	String getSecret();
	
	void setSecret(String pass);

	void setCrypted(boolean status);
}
