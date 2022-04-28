package io.github.nabil.ptolemy.rcp.crypto.providers;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;

import io.github.nabil.ptolemy.rcp.crypto.exception.CryptoInitializationException;
import io.github.nabil.ptolemy.rcp.crypto.exception.DecryptionException;
import io.github.nabil.ptolemy.rcp.crypto.exception.EncryptionException;

public class NoCryptoProvider extends CryptoProvider {
	public NoCryptoProvider(Logger logger) {
		super(null, logger);
	}

	@Override
	public boolean init(String username, String pass) throws CryptoInitializationException {
		return true;
	}

	@Override
	public String encrypt(String plainText) throws EncryptionException {
		return null;
	}

	@Override
	public String decrypt(String encryptedValue, String pass) throws DecryptionException {
		return null;
	}

	@Override
	public SecretKeySpec generateKey(String password) throws CryptoInitializationException {
		return null;
	}

	@Override
	public boolean verify(String source, String password) {
		return true;
	}

	@Override
	public SecretKeySpec getGlobalKey() {
		return null;
	}

}
