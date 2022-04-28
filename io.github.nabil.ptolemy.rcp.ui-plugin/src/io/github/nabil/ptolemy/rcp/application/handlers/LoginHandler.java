package io.github.nabil.ptolemy.rcp.application.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.dialogs.LoginDialog;

public class LoginHandler {

	@Execute
	public void execute(Shell shell) {
		LoginDialog dialog = new LoginDialog(shell);

		// get the new values from the dialog
		if (dialog.open() == Window.OK) {
			// String user = dialog.getUsername();
			// String pw = dialog.getPassword();
		}
	}
}