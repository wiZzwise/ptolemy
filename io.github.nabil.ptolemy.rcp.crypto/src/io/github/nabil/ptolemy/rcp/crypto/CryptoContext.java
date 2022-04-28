package io.github.nabil.ptolemy.rcp.crypto;

import org.slf4j.Logger;

import io.github.nabil.ptolemy.rcp.crypto.exception.CryptoInitializationException;
import io.github.nabil.ptolemy.rcp.crypto.exception.DecryptionException;
import io.github.nabil.ptolemy.rcp.crypto.exception.EncryptionException;
import io.github.nabil.ptolemy.rcp.crypto.providers.CryptoProvider;
import io.github.nabil.ptolemy.rcp.crypto.providers.NoCryptoProvider;

public class CryptoContext {

	private CryptoProvider cryptoProvider;

	public CryptoContext(Logger logger) {
		this.cryptoProvider = new NoCryptoProvider(logger);
	}

	public boolean init(String username, String pass) throws CryptoInitializationException {
		return this.cryptoProvider.init(username, pass);
	}

	public String encrypt(String plainText) throws EncryptionException {
		return this.cryptoProvider.encrypt(plainText);
	}

	public String decrypt(String encryptedValue, String pass) throws DecryptionException {
		return this.cryptoProvider.decrypt(encryptedValue, pass);
	}

	public boolean verify(String source, String password) {
		return this.cryptoProvider.verify(source, password);
	}
}
