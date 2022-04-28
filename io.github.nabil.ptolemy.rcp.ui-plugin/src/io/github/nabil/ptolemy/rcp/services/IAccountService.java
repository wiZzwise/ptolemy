package io.github.nabil.ptolemy.rcp.services;

import java.util.Collection;

import io.github.nabil.ptolemy.rcp.model.Account;

public interface IAccountService {

	public boolean delete(String id);

	public Account get(String id);

	public Collection<Account> getAll();

	public boolean save(Account account);

	public String uuid();
}
