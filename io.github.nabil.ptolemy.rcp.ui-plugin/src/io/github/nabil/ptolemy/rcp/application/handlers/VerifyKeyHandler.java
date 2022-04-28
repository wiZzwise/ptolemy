package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.application.PtolemyAppContext;
import io.github.nabil.ptolemy.rcp.crypto.DecryptEntityVisitor;
import io.github.nabil.ptolemy.rcp.dialogs.PasswordValidationDialog;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

/**
 * Handler to verify if user is allowed to check key value.
 *
 * @author nabil
 * @since v0.1
 */
public class VerifyKeyHandler extends PtolemyUICommandHandler {

	@Inject
	private IAccountService				s_accountService;

	@Inject
	private IEventBroker				_broker;

	private PasswordValidationDialog	passwdDialog;

	@CanExecute
	public boolean canExecute() {
		return (this.s_accountService != null);
	}

	@Execute
	public void execute(Shell shell, IEclipseContext eclipseContext, ParameterizedCommand command) {

		String id = (String) command.getParameterMap().get(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID);
		String action = (String) command.getParameterMap().get(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION);

		logger().debug("Opening dialog for = {}", id);

		Account account = null;
		if (id != null) {
			account = s_accountService.get(id);
		}

		if (account == null) {
			logger().error("No Account found for ID= {}", id);
			return;
		}

		this.passwdDialog = new PasswordValidationDialog(shell);

		boolean open = true;
		this.passwdDialog.create();
		while (open) {
			if (this.passwdDialog.open() == Window.OK) {
				Account tmpa = null;
				if (!account.isCrypted()) {
					tmpa = account;
				} else {
					tmpa = decryptAccount(account, this.passwdDialog.getPassword());
				}

				if (tmpa != null && !tmpa.isCrypted()) {
					if (StringUtils.equalsIgnoreCase(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION_COPY_CB,
					        action)) {
						_broker.send(IUIModelConstants.PTO_TOPIC_KEY_VERIFED_FOR_CB_OK, tmpa);
					} else {
						_broker.send(IUIModelConstants.PTO_TOPIC_KEY_VERIFED_OK, tmpa.getSecret());
					}
					open = false;
				} else {
					this.passwdDialog.create();
					this.passwdDialog.setMessage("Wrong Password, please try again!", IMessageProvider.ERROR);
				}
			} else {
				open = false;
			}
		}

		this.passwdDialog.clearPassword();
		this.passwdDialog.close();
	}

	private Account decryptAccount(Account account, String pass) {
		try {
			Account tmp = new Account();
			tmp.setSecret(account.getSecret());
			tmp.setCrypted(true);
			DecryptEntityVisitor v = new DecryptEntityVisitor(pass, PtolemyAppContext.getCryptoContext());
			tmp.accept(v);
			return tmp;
		} catch (Exception ex) {
			logger().debug(ex.getMessage(), ex);
		}

		return null;
	}
}
