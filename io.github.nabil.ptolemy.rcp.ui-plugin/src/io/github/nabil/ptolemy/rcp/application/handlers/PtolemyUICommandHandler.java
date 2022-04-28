package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.parts.PartsHelper;

public abstract class PtolemyUICommandHandler {

	private final Logger	logger;

	@Inject
	protected PartsHelper	partsHelper;

	protected PtolemyUICommandHandler() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	protected Logger logger() {
		return this.logger;
	}
}
