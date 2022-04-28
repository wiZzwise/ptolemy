package io.github.nabil.ptolemy.rcp.providers;

import java.util.Collections;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.nabil.ptolemy.rcp.services.IAccountService;

public class AccountDataContentProvider implements IStructuredContentProvider {

	private IAccountService accountService;

	private Logger logger = LoggerFactory.getLogger(AccountDataContentProvider.class);

	private static final AccountDataContentProvider INSTANCE = new AccountDataContentProvider();

	public static AccountDataContentProvider getInstance() {
		return AccountDataContentProvider.INSTANCE;
	}

	private AccountDataContentProvider() {
		logger.debug("Account Data Provider Initialized");
	}

	public void init(IAccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		logger.debug("Account Data Provider Elements Requested");

		logger.debug("input element: {}", inputElement);
		return accountService != null && accountService.getAll() != null ? accountService.getAll().toArray()
				: Collections.emptyList().toArray();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		logger.debug("Account Data Provider Input changed");
		logger.debug("input element: {}", oldInput);
		logger.debug("input element: {}", newInput);

		IStructuredContentProvider.super.inputChanged(viewer, oldInput, newInput);
	}
}
