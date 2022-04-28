package io.github.nabil.ptolemy.rcp.application.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.PtolemyApplicationContext;
import io.github.nabil.ptolemy.rcp.application.handlers.PtolemyUICommandHandler;
import io.github.nabil.ptolemy.rcp.filters.AccountFilter;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;
import io.github.nabil.ptolemy.rcp.providers.AccountDataContentProvider;

/**
 * List of accounts
 *
 * @author nabil
 * @since 0.1
 */
public class KeysPart extends PtolemyUICommandHandler {

	private Logger			LOG	= LoggerFactory.getLogger(this.getClass());

	private Text			_searchInput;

	private TableViewer		_keysViewer;

	@Inject
	private MDirtyable		_dirty;

	@Inject
	private MApplication	_application;

	@Inject
	private Shell			_shell;

	@Inject
	@Optional
	@Named(IUIModelConstants.KEYS_PART_ID)
	MPart					_keysPart;

	private Account			focusedAccount;

	private AccountFilter	filter;

	@Inject
	private PtolemyApplicationContext ptolemyApplicationContext;

	@Inject
	private AccountDataContentProvider	accountDataContentProvider;
	
	@PostConstruct
	public void createComposite(Composite parent, EMenuService menuService) {

		parent.setLayout(new GridLayout(1, false));

		_searchInput = new Text(parent, SWT.BORDER);
		_searchInput.setMessage("Enter text to search!");
		_searchInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				// _dirty.setDirty(true);
			}
		});
		_searchInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite tableComposite = new Composite(parent, SWT.FILL | SWT.V_SCROLL);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);

		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		tableComposite.setLayoutData(layoutData);

		_keysViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION
		        | SWT.BORDER);
		_keysViewer.getTable().setLinesVisible(true);
		_keysViewer.getTable().setHeaderVisible(true);

		// create the columns
		partsHelper.createAccountsTableColumns(_keysViewer, tableColumnLayout);

		_keysViewer.setContentProvider(accountDataContentProvider);
		_keysViewer.setInput(accountDataContentProvider.getElements(null));

		// make lines and header visible
		final Table table = _keysViewer.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		_keysViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = _keysViewer.getStructuredSelection();
				setSelectedAccount((Account) selection.getFirstElement());
				updateAndPropagateFocusedAccount();
			}
		});
		_keysViewer.addOpenListener(new IOpenListener() {

			@Override
			public void open(OpenEvent event) {
				IStructuredSelection selection = _keysViewer.getStructuredSelection();
				setSelectedAccount((Account) selection.getFirstElement());

				updateAndPropagateFocusedAccount();
				partsHelper.openEditAccountDialog(focusedAccount.getId());
			}
		});

		_searchInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				// TODO: Clean search pattern
				filter.setSearchText(_searchInput.getText());
				_keysViewer.refresh();
			}

		});
		filter = new AccountFilter();
		_keysViewer.addFilter(filter);

		// Create a menu manager and create context menu
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(_keysViewer.getTable());
		// set the menu on the SWT widget
		_keysViewer.getTable().setMenu(menu);
		// register the menu with the framework
		menuService.registerContextMenu(_keysViewer.getControl(), "io.github.nabil.ptolemy.menu.popupmenu.0");

	}

	public Account getSelectedAccount() {
		IStructuredSelection selection = _keysViewer.getStructuredSelection();
		return (Account) selection.getFirstElement();
	}

	@Inject
	@Optional
	public void setCurrentAccount(@Named(IUIModelConstants.SELECTED_ACCOUNT) Account account) {

		if (account != null && this.focusedAccount != null
		        && StringUtils.equals(this.focusedAccount.getId(), account.getId())) {

			this.focusedAccount.setName(account.getName());
			this.focusedAccount.setUserid(account.getUserid());
			this.focusedAccount.setSecret(account.getSecret());
			this.focusedAccount.setDescription(account.getDescription());
			this.focusedAccount.setNotes(account.getNotes());

		} else {
			this.focusedAccount = account;
		}

		updateViewer();
		return;
	}

	public void updateViewer() {
		_dirty.setDirty(ptolemyApplicationContext.storageManager().isCurrentDbDirty());
		_keysViewer.refresh();
	}

	@Inject
	@Optional
	public void getEvent(@UIEventTopic(IUIModelConstants.PTO_TOPIC_KEY_VERIFED_FOR_CB_OK) Account account) {
		final Clipboard cb = new Clipboard(_shell.getDisplay());
		TextTransfer textTransfer = TextTransfer.getInstance();
		cb.setContents(new Object[] { account.getSecret() }, new Transfer[] { textTransfer });
		logger().debug("Account Details: {}", account);
	}

	@Inject
	@Optional
	public void rcvEvtUpdatedAccount(@UIEventTopic(IUIModelConstants.PTO_TOPIC_UPDATE_ACCOUNT_OK) String id) {
		updateViewer();
	}

	@Inject
	@Optional
	public void rcvEvtDeletedAccount(@UIEventTopic(IUIModelConstants.PTO_TOPIC_DELETED_ACCOUNT_OK) String id) {
		updateViewer();
	}

	@Persist
	public void save() {
		_dirty.setDirty(false);
		LOG.debug("Action Saved!");
	}

	@Focus
	public void setFocus() {
		// _searchInput.setFocus();
		_keysViewer.getControl().setFocus();
	}

	public void setSelectedAccount(Account account) {
		this.focusedAccount = account;
	}

	public void updateAndPropagateFocusedAccount() {
		if (this.focusedAccount != null) {
			LOG.debug("==> " + focusedAccount.getName());
			_application.getContext().set(IUIModelConstants.SELECTED_ACCOUNT, this.focusedAccount);
		}
	}
}