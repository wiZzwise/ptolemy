package io.github.nabil.ptolemy.rcp.application.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;
import io.github.nabil.ptolemy.rcp.crypto.exception.EncryptionException;
import io.github.nabil.ptolemy.rcp.storage.StorageManager;

public class OpenDatabaseStep extends SequenceStep {
		
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean execute(PtolemyApplicationContext ptolemyApplicationContext) {
		StorageManager storageManager = new StorageManager(ptolemyApplicationContext);
		
		try {
			if(storageManager.init()) {
				logger.debug("Database Opened");
				ptolemyApplicationContext.update(storageManager);

				return nextStep(ptolemyApplicationContext); 
			}
		} catch (EncryptionException e) {
			logger.error("Unable to open Database: {}", e.getMessage(), e);
		}
		
		return true;
	}
}
