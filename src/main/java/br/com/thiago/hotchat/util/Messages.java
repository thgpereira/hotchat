package br.com.thiago.hotchat.util;

import java.util.ResourceBundle;

public class Messages {

	public static String emailRegistered() {
		return getMessage("user.email.registered");
	}

	public static String errorLoadHistory() {
		return getMessage("message.history.error");
	}

	public static String dateStartAfterDateEnd() {
		return getMessage("message.datestart.after.dateend");
	}

	public static String userBlockAlreadyExists() {
		return getMessage("message.userblock.already.exits");
	}

	public static String userBlockNotExists() {
		return getMessage("message.userblock.not.exits");
	}

	public static String userBlockBlocedError() {
		return getMessage("message.userblock.error");
	}

	public static String processSuccess() {
		return getMessage("message.process.sucess");
	}

	public static String getMessage(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");
		return bundle.getString(key);
	}

}
