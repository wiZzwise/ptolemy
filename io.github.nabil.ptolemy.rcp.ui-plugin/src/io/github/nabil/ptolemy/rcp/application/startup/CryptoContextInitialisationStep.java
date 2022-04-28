package io.github.nabil.ptolemy.rcp.application.startup;

import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;
import io.github.nabil.ptolemy.rcp.crypto.CryptoContext;

public class CryptoContextInitialisationStep extends SequenceStep {
		
	@Override
	public boolean execute(PtolemyApplicationContext context) {
		context.update(new CryptoContext(LoggerFactory.getLogger(PtolemyApplicationContext.class)));

		return nextStep(context);
	}
}
