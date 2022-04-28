package io.github.nabil.ptolemy.rcp.model;

/**
 * UI Object Model constants
 *
 * @author nabil
 * @since 0.1
 */
public interface IUIModelConstants {

	/**
	 * The account selected in the Keys List and on the details windows in a given context.
	 * <p>
	 * This value can be <code>null</code> if there is no selected account in a given context.
	 * </p>
	 */
	public static final String	SELECTED_ACCOUNT								= "ptSelectedAccount";

	/**
	 * The keys part id
	 */
	public static final String	KEYS_PART_ID									= "io.github.nabil.ptolemy.rcp.part.keys";

	/**
	 * The details part id
	 */
	public static final String	DETAILS_PART_ID									= "io.github.nabil.ptolemy.rcp.part.details";

	// **** ACTIONS ***//
	/**
	 * The action create.
	 */
	public static final String	CMD_ACTION_CREATE								= "CREATE";

	/**
	 * The action create.
	 */
	public static final String	CMD_ACTION_EDIT									= "EDIT";

	// **** COMMANDS ***//
	/**
	 * The action the Account.
	 */
	public static final String	CMD_CREATE_ACCOUNT								= "io.github.nabil.ptolemy.commands.account.create";

	public static final String	CMD_EDIT_ACCOUNT								= "io.github.nabil.ptolemy.commands.account.edit";

	public static final String	CMD_VERIFY_KEY									= "io.github.nabil.ptolemy.commands.security.verifykey";

	public static final String	CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ID				= "io.github.nabil.ptolemy.commands.parameters.account.id";

	public static final String	CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION			= "io.github.nabil.ptolemy.commands.parameters.account.action";

	public static final String	CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION_SHOW		= "SHOW";

	public static final String	CMD_EDIT_ACOUNT_PARAM_ACCOUNT_ACTION_COPY_CB	= "CPCB";

	// **** EVENTS CONSTANTS ***//
	public static final String	PTO_TOPIC										= "PTO_TOPIC/*";

	public static final String	PTO_TOPIC_KEY_VERIFED_OK						= "PTO_TOPIC/KEY_VERIFED/OK";

	public static final String	PTO_TOPIC_KEY_VERIFED_KO						= "PTO_TOPIC/KEY_VERIFED/KO";

	public static final String	PTO_TOPIC_KEY_VERIFED_FOR_CB_OK					= "PTO_TOPIC/KEY_VERIFED/OK";

	public static final String	PTO_TOPIC_DELETED_ACCOUNT_OK					= "PTO_TOPIC/ACTION/DELETE/OK";

	public static final String	PTO_TOPIC_UPDATE_ACCOUNT_OK						= "PTO_TOPIC/ACTION/UPDATE/OK";
}
