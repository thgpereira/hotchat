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

	public static String getMessage(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");
		return bundle.getString(key);
	}

}
