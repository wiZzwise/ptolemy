package io.github.nabil.ptolemy.rcp.storage;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;
import io.github.nabil.ptolemy.rcp.crypto.exception.EncryptionException;
import io.github.nabil.ptolemy.rcp.exception.InvalidDbOwnerException;

public class StorageManager {
	private PtolemyDB pDb = null;
	private final PtolemyApplicationContext ptolemyApplicationContext;
	private Logger logger = LoggerFactory.getLogger(StorageManager.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	public StorageManager(PtolemyApplicationContext ptolemyApplicationContext) {
		this.ptolemyApplicationContext = ptolemyApplicationContext;

		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
	}

	@SuppressWarnings("deprecation")
	public boolean init() throws EncryptionException {

		String rawDbFileContent = null;
		try {
			rawDbFileContent = FileUtils.readFileToString(ptolemyApplicationContext.dbFile());
		} catch (Exception ex) {
			logger.error("Unable to load DB file!", ex);
		}

		if (StringUtils.isNotBlank(rawDbFileContent)) {
			try {
				pDb = objectMapper.readValue(rawDbFileContent, PtolemyDB.class);
				if (!ptolemyApplicationContext.cryptoContext().verify(pDb.getOwner(), ptolemyApplicationContext.username())) {
					throw new InvalidDbOwnerException();
				}

				return true;
			} catch (IOException ex) {
				logger.error("Unable to read db content!", ex);
			}
		} else {
			// init DB
			pDb = new PtolemyDB("v0.1", ptolemyApplicationContext.cryptoContext().encrypt(ptolemyApplicationContext.username()));
			return saveDbFile();
		}

		return false;
	}

	public PtolemyDB getPtolemyDb() {
		return pDb;
	}

	@SuppressWarnings("deprecation")
	public boolean saveDbFile() {
		if (pDb != null) {
			try {
				// Convert object to JSON string
				String rawDbContent = objectMapper.writeValueAsString(pDb);
				if (logger.isDebugEnabled()) {
					logger.debug(rawDbContent);
				}
				FileUtils.writeStringToFile(ptolemyApplicationContext.dbFile(), rawDbContent);
				pDb.setDirty(false);
				return true;
			} catch (IOException ex) {
				logger.error("Unable to save db content!", ex);
				ex.printStackTrace();
			}
		}

		return false;
	}

	public boolean isCurrentDbDirty() {
		return pDb != null && pDb.isDirty();
	}
}
