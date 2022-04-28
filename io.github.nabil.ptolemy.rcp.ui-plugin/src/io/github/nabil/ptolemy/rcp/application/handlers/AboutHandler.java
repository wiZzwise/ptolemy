package io.github.nabil.ptolemy.rcp.application.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.application.E4LifeCycle;

public class AboutHandler {

	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell,
		        "About",
		        "Ptolemy Application. Version: " + E4LifeCycle.VERSION + "\n\nThe Coder Swiss Knife. \n\n\n\nNabil M (https://github.com/nabil)");
	}
}
