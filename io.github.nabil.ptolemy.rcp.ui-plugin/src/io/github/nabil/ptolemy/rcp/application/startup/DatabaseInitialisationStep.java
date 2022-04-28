package io.github.nabil.ptolemy.rcp.application.startup;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;

public class DatabaseInitialisationStep extends SequenceStep {
		
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean execute(PtolemyApplicationContext context) {

		final File dbFile = context.dbFile(); 
		try {
			if (!dbFile.exists()) {
				dbFile.getParentFile().mkdirs();
				dbFile.createNewFile();
				
				logger.info("No database file found, created new one!");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new StartupSequenceException("Unable to create new DB file: " + ioe.getMessage());
		}

		return nextStep(context);
	}
}
