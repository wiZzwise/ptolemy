package io.github.nabil.ptolemy.rcp.services.impl;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import io.github.nabil.ptolemy.rcp.application.PtolemyAppContext;
import io.github.nabil.ptolemy.rcp.crypto.EncryptEntityVisitor;
import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.AccountID;
import io.github.nabil.ptolemy.rcp.services.IAccountService;
import io.github.nabil.ptolemy.rcp.storage.PtolemyDB;

public class AccountService implements IAccountService {

	private Map<AccountID, Account>	accounts;

	private PtolemyDB				ptolemyDb;

	public AccountService(PtolemyDB pDb) {
		this.ptolemyDb = pDb;
		this.loadData();
	}

	@Override
	public String uuid() {
		return UUID.randomUUID().toString();
	}

	@Override
	public boolean delete(String id) {
		return (this.accounts.remove(new AccountID(id)) != null && commit());
	}

	@Override
	public Account get(String id) {
		return accounts.get(new AccountID(id));
	}

	@Override
	public Collection<Account> getAll() {
		if (accounts == null || accounts.isEmpty()) {
			this.loadData();
		}

		if (accounts != null) {
			return accounts.values();
		}

		return null;
	}

	@Override
	public boolean save(Account account) {
		if (accounts != null) {
			if (StringUtils.isBlank(account.getId())) {
				account.setId(uuid());
			}
			if (!account.isCrypted()) {
				EncryptEntityVisitor v = new EncryptEntityVisitor(PtolemyAppContext.getCryptoContext());
				if (!account.accept(v)) {
					throw new RuntimeException("Unable to save account");
				}
			}

			accounts.put(new AccountID(account.getId()), account);

			commit();
			return true;
		}

		return false;
	}

	protected Map<AccountID, Account> loadData() {
		if (ptolemyDb != null) {
			accounts = ptolemyDb.loadAccounts();
		}
		return accounts;
	}

	private boolean commit() {
		if (ptolemyDb != null) {
			return ptolemyDb.saveAccounts(accounts);
		}

		return false;
	}

}
