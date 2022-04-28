package io.github.nabil.ptolemy.rcp.application.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.nabil.ptolemy.rcp.application.PtolemyAppContext;
import io.github.nabil.ptolemy.rcp.application.handlers.PtolemyUICommandHandler;
import io.github.nabil.ptolemy.rcp.crypto.EncryptEntityVisitor;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class DetailsPart extends PtolemyUICommandHandler {

	private Composite		_parent;

	private String			id;
	private Text			_description;
	private Text			_name;
	private Text			_userid;
	private Text			_secret;
	private Text			_notes;

	private Button			_saveBtn;
	private Button			_showBtn;

	private boolean			secretShown	= false;

	@Inject
	@Optional
	@Named(IUIModelConstants.KEYS_PART_ID)
	MPart					_keysPart;

	@Inject
	private MDirtyable		_dirty;

	@Inject
	Shell					_shell;

	@Inject
	private IEventBroker	_broker;

	@Inject
	@Optional
	@Named(IUIModelConstants.DETAILS_PART_ID)
	private MPart			_detailsPart;

	private Account			currentAccount;

	@Inject
	private IAccountService	accountService;

	public void buildAccountComposite() {

		_name = new Text(this._parent, SWT.BORDER);
		_name.setMessage("Name");
		_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		_userid = new Text(this._parent, SWT.BORDER);
		_userid.setMessage("User ID");
		_userid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		_description = new Text(this._parent, SWT.BORDER);
		_description.setMessage("Description");
		_description.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		_secret = new Text(this._parent, SWT.BORDER | SWT.PASSWORD);
		_secret.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		_showBtn = new Button(this._parent, SWT.BORDER);
		_showBtn.setText("Show   ");
		_showBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT, true, false, 2, 1));
		_showBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					showSecretBtnClicked();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		_notes = new Text(this._parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gridData.minimumHeight = 100;
		_notes.setSize(100, 1000);
		_notes.setLayoutData(gridData);
		_notes.setMessage("Notes");

		_saveBtn = new Button(this._parent, SWT.RIGHT);
		_saveBtn.setText("Add/Update");
		_saveBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT, true, false, 2, 1));
		_saveBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
	}

	@PostConstruct
	public void createControls(Composite parent) {
		this._parent = parent;
		this._parent.setLayout(new GridLayout(2, false));
		this.buildAccountComposite();
	}

	public void flipSecretDisplayMode(boolean status) {

		if (this._detailsPart != null) {
			String psswd = _secret.getText();
			_secret.dispose();

			if (status) {
				_showBtn.setText("Hide");
				_secret = new Text(this._parent, SWT.BORDER);
			} else {
				_showBtn.setText("Show");
				_secret = new Text(this._parent, SWT.BORDER | SWT.PASSWORD);
			}
			_showBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.RIGHT, true, false, 2, 1));

			_secret.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			_secret.setText(psswd);
			_secret.moveBelow(this._parent.getChildren()[2]);

			this._parent.layout(true, true);
			this._parent.redraw();
			this._parent.update();
		}

		this.secretShown = status;
	}

	@Inject
	@Optional
	public void getEvent(@UIEventTopic(IUIModelConstants.PTO_TOPIC_KEY_VERIFED_OK) String data) {
		_secret.setText(data);
		flipSecretDisplayMode(true);
	}

	@Inject
	@Optional
	public void receiveActivePart(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		if (activePart != null && IUIModelConstants.DETAILS_PART_ID.equalsIgnoreCase(activePart.getElementId())) {
			_detailsPart = activePart;
			logger().debug("Active part changed {}  ID= {}", activePart.getLabel(), activePart.getElementId());
		}
	}

	@Persist
	public void save() {

		String n = _name.getText();
		String u = _userid.getText();
		String d = _description.getText();
		String no = _notes.getText();
		String se = _secret.getText();

		if (n.isEmpty() || u.isEmpty()) {
			MessageDialog.openInformation(_shell, "Alert", "Account Name and User ID are m!");
			return;
		}

		try {
			Account modAccount = new Account(id, n, u, d, no, se);
			modAccount.setCrypted(currentAccount.isCrypted());
			if (currentAccount != null && !StringUtils.equals(se, currentAccount.getSecret())) {
				modAccount.setCrypted(false);
				EncryptEntityVisitor v = new EncryptEntityVisitor(PtolemyAppContext.getCryptoContext());
				if (!modAccount.accept(v)) {
					throw new RuntimeException("Unable to save account");
				}
			}

			if (accountService.save(modAccount)) {
				_broker.send(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK, modAccount.getId());
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		_dirty.setDirty(false);
		logger().debug("Details part Saved!");
	}

	@Inject
	@Optional
	public void setAccount(@Named(IUIModelConstants.SELECTED_ACCOUNT) Account account) {

		flipSecretDisplayMode(false);

		id = account.getId();
		_name.setText(account.getName());
		_userid.setText(account.getUserid());
		_description.setText(account.getDescription());
		_notes.setText(account.getNotes());
		_secret.setText(account.getSecret());

		this.currentAccount = account;
	}

	@Inject
	@Optional
	public void rcvEvtUpdatedAccount(@UIEventTopic(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK) String sid) {
		if (StringUtils.isNotBlank(sid)) {
			setAccount(accountService.get(sid));
		}
	}

	public void showSecretBtnClicked() {
		if (!this.secretShown) {
			partsHelper.callVerifyKeyCmd(id, IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION_SHOW);
		} else {
			flipSecretDisplayMode(this.secretShown = !this.secretShown);
		}
	}
}
