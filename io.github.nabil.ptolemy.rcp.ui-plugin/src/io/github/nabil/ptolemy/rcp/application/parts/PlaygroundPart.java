package io.github.nabil.ptolemy.rcp.application.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class PlaygroundPart {

	private Composite		_parent;
	private Browser			_browser;

	private Account			selected	= null;

	@Inject
	private IAccountService	s_accountService;

	@PostConstruct
	public void createControls(Composite parent) {
		this._parent = parent;
		parent.setLayout(new GridLayout(2, false));

		try {
			_browser = new Browser(parent, SWT.NONE);
			if (this.selected != null) {
				_browser.setText(this.selected.getNotes());
			}
			_browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Focus
	public void onFocus() {

	}

	@Inject
	@Optional
	public void setAccount(@Named(IUIModelConstants.SELECTED_ACCOUNT) Account account) {
		this.selected = account;
		this._browser.setText(this.selected.getNotes());
		this._parent.redraw();
	}

	@Inject
	@Optional
	public void rcvEvtUpdatedAccount(@UIEventTopic(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK) String sid) {
		if (this.selected != null && StringUtils.equalsIgnoreCase(this.selected.getId(), sid)) {
			this.selected = s_accountService.get(sid);
			this._browser.setText(this.selected.getNotes());
			this._parent.redraw();
		}
	}
}
