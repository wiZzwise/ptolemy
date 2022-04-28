package io.github.nabil.ptolemy.rcp.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Account ID Managed by Ptolemy
 *
 * @author nabil
 * @since 0.1
 */
public class AccountID implements Comparable<AccountID> {

	@JsonProperty
	private String id = null;

	public AccountID() {
	}

	public AccountID(String id) {
		this.id = id;
	}

	@JsonGetter
	public String getId() {
		return this.id;
	}

	@JsonSetter
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(AccountID o) {
		if (this.id != null && o != null) {
			return this.id.compareTo(o.getId());
		} else if (this.id != null) {
			return 1;
		} else
			return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		AccountID nextId = (AccountID) obj;
		if (id != null) {
			return id.equals(nextId.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}

		return 0;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
