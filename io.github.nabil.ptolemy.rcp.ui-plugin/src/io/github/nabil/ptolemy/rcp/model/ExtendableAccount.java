package io.github.nabil.ptolemy.rcp.model;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtendableAccount implements Serializable {

	/**
	 * Serial Version ID
	 */
	private static final long		serialVersionUID	= -868197268232661135L;

	@JsonProperty(value = "attributes", required = true)
	private HashMap<String, String>	attributes;

	public ExtendableAccount() {

	}

	public ExtendableAccount(int size) {
		this.attributes = new HashMap<String, String>(size);
	}

	public void setField(String key, String value) {
		this.attributes.put(key, value);
	}

	@JsonIgnore
	public String getField(String key) {
		if (attributes != null) {
			return attributes.get(key);
		}

		return null;
	}

	@JsonIgnore
	public String getField(int key) {
		Object[] attrs = attributes.values().toArray();
		if (attrs != null && attrs.length > key) {
			return (String) attributes.values().toArray()[key];
		}
		return null;
	}

	public HashMap<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}

}
