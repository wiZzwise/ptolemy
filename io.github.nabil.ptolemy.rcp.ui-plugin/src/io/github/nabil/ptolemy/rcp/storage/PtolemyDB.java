package io.github.nabil.ptolemy.rcp.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.github.nabil.ptolemy.rcp.model.Account;
import io.github.nabil.ptolemy.rcp.model.AccountConverter;
import io.github.nabil.ptolemy.rcp.model.AccountID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PtolemyDB implements Serializable {

	private static final long	serialVersionUID	= 3970616781973432471L;

	public String				owner;

	public String				version;

	@JsonIgnore
	private boolean				dirty;

	public PtolemyDB() {
	}

	public PtolemyDB(String version) {
		this.version = version;
	}

	public PtolemyDB(String version, String owner) {
		this.version = version;
		this.owner = owner;
	}

	@JsonProperty(value = "data", required = true)
	private List<PtolemyStore> data = new ArrayList<PtolemyStore>();

	@JsonIgnore
	public Map<AccountID, Account> loadAccounts() {
		Map<AccountID, Account> accounts = new TreeMap<AccountID, Account>();
		if (this.data != null && !this.data.isEmpty()) {
			PtolemyStore exAccounts = this.data.get(0);
			Set<AccountID> keys = exAccounts.keySet();
			Iterator<AccountID> iter = keys.iterator();
			while (iter.hasNext()) {
				AccountID key = iter.next();
				accounts.put(key, AccountConverter.getAccountV01(exAccounts.get(key)));
			}
		}
		return accounts;
	}

	@JsonIgnore
	public boolean saveAccounts(Map<AccountID, Account> accounts) {

		Set<AccountID> keys = accounts.keySet();
		Iterator<AccountID> iter = keys.iterator();
		PtolemyStore exAccounts = new PtolemyStore();
		while (iter.hasNext()) {
			AccountID key = iter.next();
			exAccounts.put(key, AccountConverter.getExtendableAccountV01(accounts.get(key)));
		}

		this.data.add(exAccounts);
		if (this.data.size() > 1) {
			this.data.remove(0);
		}

		this.setDirty(true);
		return true;
	}

	public List<PtolemyStore> getData() {
		return this.data;
	}

	public void setData(List<PtolemyStore> data) {
		this.data = data;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
