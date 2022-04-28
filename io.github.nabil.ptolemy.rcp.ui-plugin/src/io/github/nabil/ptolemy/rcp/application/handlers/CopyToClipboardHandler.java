package io.github.nabil.ptolemy.rcp.application.handlers;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.Shell;

import io.github.nabil.ptolemy.rcp.application.parts.KeysPart;
import io.github.nabil.ptolemy.rcp.application.parts.PartsHelper;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

/**
 * Handler for M1+K request to copy to clipboard password for selected row.
 *
 * @author nabil
 * @since v0.1
 */
public class CopyToClipboardHandler extends PtolemyUICommandHandler {

	@Inject
	private IAccountService	s_accountService;

	@Inject
	private PartsHelper		partsHelper;

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
			if (account != null && StringUtils.isNotEmpty(account.getSecret())) {
				partsHelper.callVerifyKeyCmd(account.getId(),
				        IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION_COPY_CB);
			}
		}
	}
}
