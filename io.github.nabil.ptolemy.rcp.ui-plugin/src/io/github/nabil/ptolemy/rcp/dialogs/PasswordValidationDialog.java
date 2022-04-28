package io.github.nabil.ptolemy.rcp.dialogs;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class PasswordValidationDialog extends TitleAreaDialog {

	private Text	txtPassowrd;

	private String	password;

	private Shell	_shell;

	public PasswordValidationDialog(Shell parentShell) {
		super(parentShell);

		this._shell = parentShell;
	}

	public void clearPassword() {
		password = "";
	}

	@Override
	public void create() {
		super.create();
		setTitle("Password Validation");
		setMessage("Please enter your password", IMessageProvider.INFORMATION);
	}

	@Override
	public Shell getParentShell() {
		return _shell;
	}

	public String getPassword() {
		return this.password;
	}

	public void notifyWrongPassword() {

	}

	private void createPassowrd(Composite container) {
		Label lbtPasswd = new Label(container, SWT.NONE);
		lbtPasswd.setText("Password");

		GridData dataPasswd = new GridData();
		dataPasswd.grabExcessHorizontalSpace = true;
		dataPasswd.horizontalAlignment = GridData.FILL;

		txtPassowrd = new Text(container, SWT.BORDER | SWT.PASSWORD);
		txtPassowrd.setLayoutData(dataPasswd);
	}

	private void saveInput() {
		password = txtPassowrd.getText();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createPassowrd(container);

		return area;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
}