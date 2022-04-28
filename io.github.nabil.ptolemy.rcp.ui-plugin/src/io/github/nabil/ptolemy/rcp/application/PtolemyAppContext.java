package io.github.nabil.ptolemy.rcp.application;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.nabil.ptolemy.rcp.crypto.CryptoContext;
import io.github.nabil.ptolemy.rcp.exception.InvalidDbOwnerException;
import io.github.nabil.ptolemy.rcp.storage.PtolemyDB;

public class PtolemyAppContext {

	private static PtolemyDB pDb = null;
	private static CryptoContext cryptoContext = null;
	private static Logger logger = LoggerFactory.getLogger(PtolemyAppContext.class);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {

		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);

		cryptoContext = new CryptoContext(logger);
	}

	public static boolean init(String username, String pass) throws Exception {

		if (!cryptoContext.init(username, pass)) {
			return false;
		}

		String rawDbFileContent = null;
		try {
			rawDbFileContent = FileUtils.readFileToString(E4LifeCycle.DB_FILE);
		} catch (Exception ex) {
			logger.error("Unable to load DB file!", ex);
		}

		if (StringUtils.isNotBlank(rawDbFileContent)) {
			try {
				pDb = objectMapper.readValue(rawDbFileContent, PtolemyDB.class);
				if (!cryptoContext.verify(pDb.getOwner(), username)) {
					throw new InvalidDbOwnerException();
				}

				return true;
			} catch (IOException ex) {
				logger.error("Unable to read db content!", ex);
			}
		} else {
			// init DB
			pDb = new PtolemyDB("v0.1", cryptoContext.encrypt(username));
			return saveDbFile();
		}

		return false;
	}

	public static PtolemyDB getPtolemyDb() {
		return pDb;
	}

	public static CryptoContext getCryptoContext() {
		return cryptoContext;
	}

	public static boolean saveDbFile() {
		if (pDb != null) {
			try {
				// Convert object to JSON string
				String rawDbContent = objectMapper.writeValueAsString(pDb);
				if (logger.isDebugEnabled()) {
					logger.debug(rawDbContent);
				}
				FileUtils.writeStringToFile(E4LifeCycle.DB_FILE, rawDbContent);
				pDb.setDirty(false);
				return true;
			} catch (IOException ex) {
				logger.error("Unable to save db content!", ex);
				ex.printStackTrace();
			}
		}

		return false;
	}

	public static boolean isCurrentDbDirty() {
		return pDb != null && pDb.isDirty();
	}
}
