package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.dialogs.AccountDialog;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class AccountHandler extends PtolemyUICommandHandler {

	@Inject
	private MApplication	_application;

	@Inject
	private IAccountService	s_accountService;

	@Inject
	private IEventBroker	_broker;

	public AccountHandler() {
		super();
	}

	@CanExecute
	public boolean canExecute() {
		return (this.s_accountService != null);
	}

	@Execute
	public void execute(Shell shell, IEclipseContext eclipseContext, ParameterizedCommand command) {

		String sid = (String) command.getParameterMap().get(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID);

		Account account = null;
		if (StringUtils.isNotBlank(sid)) {
			account = s_accountService.get(sid);
		}

		AccountDialog dialog = new AccountDialog(shell, account);
		dialog.create();

		if (dialog.open() == Window.OK) {
			account = dialog.getAccount();
			if (s_accountService.save(account)) {
				_broker.send(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK, account.getId());
			}
			_application.getContext().set(IUIModelConstants.SELECTED_ACCOUNT, account);
			eclipseContext.get(IEventBroker.class).send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC,
			        UIEvents.ALL_ELEMENT_ID);
		}
	}
}
