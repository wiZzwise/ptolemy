package io.github.nabil.ptolemy.rcp.model;

/**
 * Accounts Managed by Ptolemy
 *
 * @author nabil
 * @since 0.1
 */
public class AccountConverter {

	private final static int VERSION_01FIELDS_SIZE = 8;

	public static Account getAccountV01(ExtendableAccount ea) {
		Account account = new Account();

		if (ea.getField(IAccountFields.ID) != null) {
			account.setId(ea.getField(IAccountFields.ID));
			account.setName(ea.getField(IAccountFields.NAME));
			account.setUserid(ea.getField(IAccountFields.USERID));
			account.setDescription(ea.getField(IAccountFields.DESCRIPTION));
			account.setNotes(ea.getField(IAccountFields.NOTES));
			account.setSecret(ea.getField(IAccountFields.SECRET));
			account.setActive(Boolean.parseBoolean(String.valueOf(ea.getField(IAccountFields.ACTIVE))));
			account.setEditable(Boolean.parseBoolean(String.valueOf(ea.getField(IAccountFields.EDITABLE))));
			account.setCrypted(true);
		}
		return account;
	}

	public static ExtendableAccount getExtendableAccountV01(Account account) {
		ExtendableAccount ea = new ExtendableAccount(VERSION_01FIELDS_SIZE);

		ea.setField(IAccountFields.ID, account.getId());
		ea.setField(IAccountFields.NAME, account.getName());
		ea.setField(IAccountFields.USERID, account.getUserid());
		ea.setField(IAccountFields.DESCRIPTION, account.getDescription());
		ea.setField(IAccountFields.NOTES, account.getNotes());
		ea.setField(IAccountFields.SECRET, account.getSecret());
		ea.setField(IAccountFields.ACTIVE, String.valueOf(account.isActive()));
		ea.setField(IAccountFields.EDITABLE, String.valueOf(account.isEditable()));

		return ea;
	}
}
