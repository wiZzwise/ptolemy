package io.github.nabil.ptolemy.rcp.crypto.providers;

import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;

import io.github.nabil.ptolemy.rcp.crypto.exception.CryptoInitializationException;
import io.github.nabil.ptolemy.rcp.crypto.exception.DecryptionException;
import io.github.nabil.ptolemy.rcp.crypto.exception.EncryptionException;

public abstract class CryptoProvider {

	protected Cipher masterCipher;
	protected SecretKeySpec masterKey;

	protected final Logger logger;
	protected final String encryptionAlgo;
	protected final Random random = new SecureRandom();

	protected CryptoProvider(String encryptionAlgo, Logger logger) {
		this.encryptionAlgo = encryptionAlgo;
		this.logger = logger;
	}

	public abstract boolean init(String username, String pass) throws CryptoInitializationException;

	public abstract String encrypt(String plainText) throws EncryptionException;

	public abstract String decrypt(String encryptedValue, String pass) throws DecryptionException;

	public abstract SecretKeySpec generateKey(String password) throws CryptoInitializationException;

	public abstract boolean verify(String source, String password);

	public abstract SecretKeySpec getGlobalKey();
}