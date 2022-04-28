package io.github.nabil.ptolemy.rcp.application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void start(BundleContext context) throws Exception {
		logger.info("Starting bundle");
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logger.info("Stopping bundle");
	}
}