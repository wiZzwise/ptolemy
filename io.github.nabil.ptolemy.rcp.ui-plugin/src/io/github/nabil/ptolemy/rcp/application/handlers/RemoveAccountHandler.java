package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.application.parts.KeysPart;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class RemoveAccountHandler extends PtolemyUICommandHandler {

	@Inject
	private IEventBroker	_broker;

	@Inject
	private IAccountService	s_accountService;

	public RemoveAccountHandler() {
		super();
	}

	@CanExecute
	public boolean canExecute() {
		return (this.s_accountService != null);
	}

	@Execute
	public void execute(Shell shell, IEclipseContext eclipseContext, ParameterizedCommand command,
	                    EPartService partService) {

		Account account = null;
		String sid = (String) command.getParameterMap().get(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID);

		if (StringUtils.isBlank(sid)) {
			MPart active = partService.getActivePart();
			if (active != null && active.getObject() instanceof KeysPart) {
				account = ((KeysPart) active.getObject()).getSelectedAccount();
			}
		} else if (StringUtils.isNotBlank(sid)) {
			account = s_accountService.get(sid);
		}

		if (account != null && MessageDialog.openConfirm(shell,
		        "Confirmation",
		        "Do you want to delete account " + account.getName() + "?")) {
			if (s_accountService.delete(account.getId())) {
				_broker.send(IUIModelConstants.PTO_TOPIC_DELETED_ACCOUNT_OK, sid);
			}
		}

		return;
	}
}
