package io.github.nabil.ptolemy.rcp.dialogs;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.nabil.ptolemy.rcp.model.Account;

public class AccountDialog extends TitleAreaDialog {

	private Composite	_parent;

	private Text		_description;
	private Text		_name;
	private Text		_userid;
	private Text		_secret;
	private Text		_notes;

	private Label		_lblName;
	private Label		_lblUserId;

	private Account		account;

	private boolean		editAccount;

	private String		actionBtnLabel	= "Create";

	private boolean		validInput		= true;

	public AccountDialog(Shell parentShell) {
		this(parentShell, null);
	}

	public AccountDialog(Shell parentShell, Account selectedAccount) {
		super(parentShell);
		this.account = selectedAccount;
		if (account != null && StringUtils.isNotBlank(account.getId())) {
			this.editAccount = true;
			this.actionBtnLabel = "Update";
		} else {
			this.account = new Account();
		}
	}

	@Override
	public void create() {
		super.create();
		if (this.editAccount) {
			setTitle("Edit Account");
		} else {
			setTitle("Add Account");
		}
		setMessage("Enter the Account details", IMessageProvider.INFORMATION);
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private void createAccountArea(Composite container) {
		_lblName = new Label(container, SWT.NONE);
		_lblName.setText("Name");

		_name = new Text(container, SWT.BORDER);
		_name.setText(this.editAccount ? this.account.getName() : "");
		_name.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_name.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				account.setName(textWidget.getText());
				isValidInput();
			}
		});

		_lblUserId = new Label(container, SWT.NONE);
		_lblUserId.setText("User ID");

		_userid = new Text(container, SWT.BORDER);
		_userid.setText(this.editAccount ? this.account.getUserid() : "");
		_userid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_userid.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				account.setUserid(textWidget.getText());
				isValidInput();
			}
		});

		Label lblDescription = new Label(container, SWT.NONE);
		lblDescription.setText("Description");

		_description = new Text(container, SWT.BORDER);
		_description.setText(this.editAccount ? this.account.getDescription() : "");
		_description.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_description.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				account.setDescription(textWidget.getText());
			}
		});

		Label lblSecret = new Label(container, SWT.NONE);
		lblSecret.setText("Secret");

		_secret = new Text(container, SWT.BORDER | SWT.PASSWORD);
		_secret.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_secret.setText(this.editAccount ? this.account.getSecret() : "");
		_secret.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				account.setSecret(textWidget.getText());
			}
		});

		Label lblNotes = new Label(container, SWT.NONE);
		lblNotes.setText("Notes");

		_notes = new Text(container, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gridData.minimumHeight = 100;
		_notes.setSize(100, 1000);
		_notes.setLayoutData(gridData);
		_notes.setText(this.editAccount ? this.account.getNotes() : "");
		_notes.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				Text textWidget = (Text) e.getSource();
				account.setNotes(textWidget.getText());
			}
		});
	}

	private boolean isValidInput() {

		Display display = _parent.getDisplay();
		validInput = true;

		if (StringUtils.isEmpty(this._name.getText())) {
			_lblName.setForeground(new Color(display, 255, 0, 0));
			validInput = false;
		} else {
			_lblName.setForeground(new Color(display, 0, 0, 0));
		}

		if (StringUtils.isEmpty(this._userid.getText())) {
			_lblUserId.setForeground(new Color(display, 255, 0, 0));
			validInput = false;
		} else {
			_lblUserId.setForeground(new Color(display, 0, 0, 0));
		}

		return validInput;
	}

	// override method to use "Login" as label for the OK button
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, this.actionBtnLabel, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		this._parent = parent;

		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createAccountArea(container);
		isValidInput();

		return area;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(600, 400);
	}

	@Override
	protected void okPressed() {
		if (isValidInput()) {
			super.okPressed();
			return;
		}

		setMessage("Please fill mandatory fields!", IMessageProvider.ERROR);
	}
}