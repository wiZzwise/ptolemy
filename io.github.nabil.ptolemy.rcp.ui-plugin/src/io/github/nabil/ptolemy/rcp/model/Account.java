package io.github.nabil.ptolemy.rcp.model;

import org.apache.commons.lang3.StringUtils;

import io.github.nabil.ptolemy.rcp.crypto.EncryptableEntity;
import io.github.nabil.ptolemy.rcp.crypto.EntityVisitor;

/**
 * Accounts Managed by Ptolemy
 *
 * @author nabil
 * @since 0.1
 */
public class Account implements Comparable<Account>, EncryptableEntity {

	public final static String	VERSION		= "0.1";

	private String				id;

	private String				name		= "";

	private String				userid		= "";

	private String				description	= "";

	private String				notes		= "";

	private transient String	secret		= "";

	private boolean				active;

	private boolean				editable;

	private boolean				crypted		= false;

	public Account() {

	}

	/**
	 * @param id
	 * @param name
	 * @param userid
	 * @param description
	 * @param notes
	 * @param secret
	 */
	public Account(String id, String name, String userid, String description, String notes, String secret) {
		super();
		setId(id);
		setName(name);
		setUserid(userid);
		setDescription(description);
		setNotes(notes);
		setSecret(secret);
	}

	/**
	 * @param id
	 * @param name
	 * @param userid
	 * @param description
	 * @param notes
	 * @param secret
	 * @param active
	 * @param editable
	 */
	public Account(
	        String id,
	        String name,
	        String userid,
	        String description,
	        String notes,
	        String secret,
	        boolean active,
	        boolean editable) {
		super();
		this.id = id;
		setId(id);
		setName(name);
		setUserid(userid);
		setDescription(description);
		setNotes(notes);
		setSecret(secret);
		this.active = active;
		this.editable = editable;
	}

	public Account(String name) {
		this.name = name;
	}

	public boolean accept(EntityVisitor v) {
		return v.visit(this);
	}

	@Override
	public int compareTo(Account o) {
		if (this.id != null && o != null) {
			return this.id.compareTo(o.getId());
		} else if (this.id != null) {
			return 1;
		} else
			return -1;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return this.notes;
	}

	@Override
	public String getSecret() {
		return secret;
	}

	public String getUserid() {
		return this.userid;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setDescription(String description) {
		if (description != null) {
			this.description = description;
		}
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}

	public void setNotes(String notes) {
		if (notes != null) {
			this.notes = notes;
		}
	}

	public void setSecret(String secret) {
		if (secret != null) {
			this.secret = secret;
		}
	}

	public void setUserid(String userid) {
		if (userid != null) {
			this.userid = userid;
		}
	}

	public boolean isCrypted() {
		return this.crypted;
	}

	public void setCrypted(boolean crypted) {
		this.crypted = crypted;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(this.getClass() != obj.getClass()) {
			return false;
		}
		
		return StringUtils.equals(id, ((Account) obj).getId());
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return this.name + "(" + this.id + ")";
	}
}
