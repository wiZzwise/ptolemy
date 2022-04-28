package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.application.parts.KeysPart;
import io.github.nabil.ptolemy.rcp.dialogs.AccountDialog;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class ShowSelectedAccountHandler extends PtolemyUICommandHandler {

	@Inject
	private MApplication	_application;

	@Inject
	private IAccountService	s_accountService;

	@Inject
	private IEventBroker	_broker;

	@CanExecute
	public boolean canExecute() {
		return (this.s_accountService != null);
	}

	@Execute
	public void execute(Shell shell, IEclipseContext eclipseContext, EPartService partService) {

		MPart active = partService.getActivePart();
		Account account = null;

		if (active != null && active.getObject() instanceof KeysPart) {
			account = ((KeysPart) active.getObject()).getSelectedAccount();
		}

		AccountDialog dialog = new AccountDialog(shell, account);

		dialog.create();

		if (dialog.open() == Window.OK) {
			account = dialog.getAccount();

			if (s_accountService.save(account)) {
				_broker.send(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK, account.getId());
			}
			_application.getContext().set(IUIModelConstants.SELECTED_ACCOUNT,
			        new Account(account.getId(), account.getName(), account.getUserid(), account.getDescription(), account.getNotes(), account.getSecret()));

			eclipseContext.get(IEventBroker.class).send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC,
			        UIEvents.ALL_ELEMENT_ID);
		}
	}
}
