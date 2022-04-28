package io.github.nabil.ptolemy.rcp.application;

import java.io.File;

import org.eclipse.equinox.app.IApplicationContext;

import io.github.nabil.ptolemy.rcp.crypto.CryptoContext;

public class PtolemyApplicationContext {

	private final File dbFile;
	private final String loggerConfigFile;
	private final IApplicationContext appContext;
	
	private CryptoContext cryptoContext;
	
	private PtolemyApplicationContext(IApplicationContext appContext, String loggerConfigFile, File dbFile) {
		this.appContext = appContext;
		this.loggerConfigFile = loggerConfigFile;
		this.dbFile = dbFile;
	}
	
	public String loggerConfigFile() {
		return this.loggerConfigFile;
	}

	public File dbFile() {
		return this.dbFile;
	}
	
	public void closeSplash() {
		this.appContext.applicationRunning();
	}

	public void update(CryptoContext cryptoContext) {
		this.cryptoContext = cryptoContext;
	}

	public CryptoContext cryptoContext() {
		return this.cryptoContext;
	}

	public static class Builder {
		
		private File dbFile;
		private String loggerConfigFile;		
		private IApplicationContext appContext;
		
		public Builder withIApplicationContext(IApplicationContext appContext) {
			this.appContext = appContext;
			return this;			
		}
		
		public Builder withDbFile(File dbFile) {
			this.dbFile = dbFile;
			return this;
		}
		
		public Builder withLoggerConfigFile(String loggerConfigFile) {
			this.loggerConfigFile = loggerConfigFile;
			return this;			
		}
		
		public PtolemyApplicationContext build() {
			return new PtolemyApplicationContext(this.appContext, this.loggerConfigFile, this.dbFile);
		}
		
	}
	
}
