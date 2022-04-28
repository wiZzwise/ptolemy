package io.github.nabil.ptolemy.rcp.application.startup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;

public class LoggerConfigurationStep extends SequenceStep {
		
	@Override
	public boolean execute(PtolemyApplicationContext context) {
		JoranConfigurator configurator = new JoranConfigurator();
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		configurator.setContext(loggerContext);
		loggerContext.reset();

		try {
			URL configFile = new URL(context.loggerConfigFile());
			InputStream configurationStream = configFile.openStream();
			configurator.doConfigure(configurationStream);
			configurationStream.close();
		} catch (JoranException | IOException e) {
			e.printStackTrace();
			return false;
		}
		
		StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext);

		return nextStep(context);
	}
}
