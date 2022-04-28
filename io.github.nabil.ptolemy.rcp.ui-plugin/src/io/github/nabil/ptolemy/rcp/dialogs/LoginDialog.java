package io.github.nabil.ptolemy.rcp.dialogs;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginDialog extends TitleAreaDialog {

	private boolean				error;

	private String				username;
	private transient String	password;

	// Widgets
	private Text				_textUsername;
	private Text				_textPassword;

	@Inject
	MApplication				application;

	public LoginDialog(Shell parentShell) {
		super(parentShell);
	}

	public LoginDialog(Shell parentShell, boolean error, String title, String message) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Login");
		setMessage("Please provide credentials");
		return contents;
	}

	// override method to use "Login" as label for the OK button
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Login", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NULL);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		new Label(container, SWT.NULL).setText("Username");
		_textUsername = new Text(container, SWT.BORDER);
		_textUsername.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(container, SWT.NULL).setText("Password");
		_textPassword = new Text(container, SWT.PASSWORD | SWT.BORDER);
		_textPassword.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return area;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void okPressed() {
		if (StringUtils.isNotBlank(_textUsername.getText()) && StringUtils.isNotBlank(_textPassword.getText())) {
			this.username = _textUsername.getText();
			this.password = _textPassword.getText();
			super.okPressed();
		} else {
			this.setErrorMessage("Username and Passwords are mandatory");
		}
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isError() {
		return this.error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}