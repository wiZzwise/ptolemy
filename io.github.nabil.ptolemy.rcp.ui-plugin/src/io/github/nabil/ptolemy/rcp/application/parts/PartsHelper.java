package io.github.nabil.ptolemy.rcp.application.parts;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.application.handlers.PtolemyUICommandHandler;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.IUIModelConstants;

@Singleton
public class PartsHelper extends PtolemyUICommandHandler {

	private IEclipseContext	_eclipseContext;

	private final Logger	LOG	= LoggerFactory.getLogger(PartsHelper.class);

	public PartsHelper(IEclipseContext context) {
		this._eclipseContext = context;
	}

	public void callVerifyKeyCmd(String id, String action) {
		try {
			ECommandService commandService = _eclipseContext.get(ECommandService.class);
			EHandlerService handlerService = _eclipseContext.get(EHandlerService.class);

			Map<String, Object> params = new HashMap<String, Object>();

			params.put(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID, String.valueOf(id));
			params.put(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION, action);

			ParameterizedCommand accountCmd = commandService.createCommand(IUIModelConstants.CMD_VERIFY_KEY, params);

			Object result = handlerService.executeHandler(accountCmd);
			if (result != null) {
				LOG.debug("Result not null");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("command with id " + IUIModelConstants.CMD_VERIFY_KEY + " not found");
		}
	}

	public void openEditAccountDialog(String id) {

		try {
			ECommandService commandService = _eclipseContext.get(ECommandService.class);
			EHandlerService handlerService = _eclipseContext.get(EHandlerService.class);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(IUIModelConstants.CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID, String.valueOf(id));

			ParameterizedCommand accountCmd = commandService.createCommand(IUIModelConstants.CMD_EDIT_ACCOUNT, params);

			Object result = handlerService.executeHandler(accountCmd);
			if (result != null) {
				LOG.debug("Result not null");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("command with id " + IUIModelConstants.CMD_EDIT_ACCOUNT + " not found");
		}
	}

	protected void createAccountsTableColumns(TableViewer tableViewer, TableColumnLayout tableColumnLayout) {
		TableViewerColumn colAccountName = new TableViewerColumn(tableViewer, SWT.NONE);
		colAccountName.getColumn().setText("Account Name");
		colAccountName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Account p = (Account) element;
				return p.getName();
			}
		});
		colAccountName.getColumn().pack();

		TableViewerColumn colAccountUserId = new TableViewerColumn(tableViewer, SWT.NONE);
		colAccountUserId.getColumn().setText("User ID");
		colAccountUserId.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Account p = (Account) element;
				return p.getUserid();
			}
		});
		colAccountUserId.getColumn().pack();

		TableViewerColumn colDesc = new TableViewerColumn(tableViewer, SWT.NONE);
		colDesc.getColumn().setText("Description");
		colDesc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Account p = (Account) element;
				return p.getDescription();
			}
		});
		colDesc.getColumn().pack();

		// Use the packed widths as the minimum widths
		int colAccountNameWidth = colAccountName.getColumn().getWidth();
		int colAccountUserIdWidth = colAccountUserId.getColumn().getWidth();
		int colDescWidth = colDesc.getColumn().getWidth();

		// Set stylesheet column to fill 100% and concept column to fit 0%, but with their packed widths as minimums
		tableColumnLayout.setColumnData(colAccountName.getColumn(),
		        new ColumnWeightData(30, colAccountNameWidth, true));
		tableColumnLayout.setColumnData(colAccountUserId.getColumn(),
		        new ColumnWeightData(30, colAccountUserIdWidth, true));
		tableColumnLayout.setColumnData(colDesc.getColumn(), new ColumnWeightData(40, colDescWidth, true));

	}
}
