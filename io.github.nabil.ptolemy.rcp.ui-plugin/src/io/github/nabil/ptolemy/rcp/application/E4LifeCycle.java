package io.github.nabil.ptolemy.rcp.application;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.parts.PartsHelper;
import io.github.nabil.ptolemy.rcp.application.startup.CryptoContextInitialisationStep;
import io.github.nabil.ptolemy.rcp.application.startup.DatabaseInitialisationStep;
import io.github.nabil.ptolemy.rcp.application.startup.LoggerConfigurationStep;
import io.github.nabil.ptolemy.rcp.application.startup.LoginStep;
import io.github.nabil.ptolemy.rcp.application.startup.OpenDatabaseStep;
import io.github.nabil.ptolemy.rcp.application.startup.StartupSequence;
import io.github.nabil.ptolemy.rcp.providers.AccountDataContentProvider;
import io.github.nabil.ptolemy.rcp.services.IAccountService;
import io.github.nabil.ptolemy.rcp.services.impl.AccountService;

/**
 * This is the implementation containing e4 LifeCycle annotated methods.<br />
 * There is a corresponding entry in <em>plugin.xml</em> (under the
 * <em>org.eclipse.core.runtime.products' extension point</em>) that references
 * this class.
 **/
public class E4LifeCycle {

	public static final String BUNDLE_NAME = FrameworkUtil.getBundle(Activator.class).getSymbolicName();

	public static final Bundle BUNDLE = Platform.getBundle(BUNDLE_NAME);

	public static final Version VERSION;

	static {
		VERSION = BUNDLE.getVersion();
	}

	public static final IPath PLUGIN_PATH = Platform.getStateLocation(BUNDLE);

	public static final String LOGBACK_CONFIG_FILE = "platform:/plugin/" + BUNDLE_NAME + "/logback.xml";

	public static final String DB_FILE_PATH = PLUGIN_PATH + "/data/ptolemy.db";

	protected static final File DB_FILE = new File(DB_FILE_PATH);

	private static Logger logger = LoggerFactory.getLogger(E4LifeCycle.class);

	private static void logRegistration(Class<?> clazz) {
		logger.debug("{} is added to the context", clazz.getName());
	}

	@PostContextCreate
	void postContextCreate(final IEventBroker eventBroker, IApplicationContext appContext,
			IEclipseContext workbenchContext) {
		logger.debug("postContextCreate called.");

		PtolemyApplicationContext ptolemyApplicationContext = new PtolemyApplicationContext.Builder()
				.withIApplicationContext(appContext)
				.withLoggerConfigFile(LOGBACK_CONFIG_FILE)
				.withDbFile(DB_FILE)
				.build();

		StartupSequence ptolemyStartupSequence = new StartupSequence.Builder()
				.with(new LoggerConfigurationStep())
				.with(new DatabaseInitialisationStep())
				.with(new CryptoContextInitialisationStep())
				.with(new LoginStep())
				.with(new OpenDatabaseStep())
				.build(ptolemyApplicationContext);

		ptolemyStartupSequence.run();

		workbenchContext.set(PtolemyApplicationContext.class.getName(), ptolemyApplicationContext);
		logRegistration(PtolemyApplicationContext.class);

		AccountService accountService =  new AccountService(ptolemyApplicationContext);
		workbenchContext.set(IAccountService.class.getName(), accountService);
		logRegistration(IAccountService.class);

		AccountDataContentProvider accountDataContentProvider = AccountDataContentProvider.getInstance();
		accountDataContentProvider.init(accountService);
		
		workbenchContext.set(AccountDataContentProvider.class.getName(), accountDataContentProvider);
		logRegistration(AccountDataContentProvider.class);

		workbenchContext.set(PartsHelper.class.getName(), new PartsHelper(workbenchContext));
		logRegistration(PartsHelper.class);
	}

	@PreSave
	void preSave(IEclipseContext workbenchContext) {
		logger.debug("preSave called.");
	}
}
