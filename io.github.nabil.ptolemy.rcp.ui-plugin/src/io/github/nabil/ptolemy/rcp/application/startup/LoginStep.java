package io.github.nabil.ptolemy.rcp.application.startup;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.PtolemyAppContext;
import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;
import io.github.nabil.ptolemy.rcp.dialogs.LoginDialog;

public class LoginStep extends SequenceStep {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean execute(PtolemyApplicationContext context) {

		context.closeSplash();

		final Shell shell = new Shell(SWT.TOOL | SWT.NO_TRIM);		
		final LoginDialog loginDialog = new LoginDialog(shell);

		boolean quit = false;
		while (!quit) {
			if (loginDialog.open() != Window.OK) {
				logger.info("Cancel Called! Good bye...");
				System.exit(-1);
			}

			String passwd = loginDialog.getPassword();
			String username = loginDialog.getUsername();

			if (StringUtils.isBlank(username) || (StringUtils.isBlank(passwd))) {
				throw new StartupSequenceException("No Username or Password were provided");
			} else {
				try {
					PtolemyAppContext.init(username, passwd);
				} catch (Exception ie) {
					logger.error(ie.getMessage(), ie);
					loginDialog.create();
					loginDialog.setMessage("Unable to open db: " + ie.getMessage(), IMessageProvider.ERROR);
					continue;
				}
				loginDialog.close();
				quit = true;
			}
		}

		return nextStep(context);
	}
}
