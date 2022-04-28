package io.github.nabil.ptolemy.rcp.storage;

import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

import io.github.nabil.ptolemy.rcp.model.AccountID;
import io.github.nabil.ptolemy.rcp.model.ExtendableAccount;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PtolemyStore extends TreeMap<AccountID, ExtendableAccount> {

	/**
	 * Serial Version UID
	 */
	private static final long	serialVersionUID	= 7322362991496346300L;

	@JsonProperty
	private String				id;

	@JsonProperty
	private String				name;

	@JsonProperty
	private String				user;

	public PtolemyStore() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, name, user);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PtolemyStore other = (PtolemyStore) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(user, other.user);
	}
}
