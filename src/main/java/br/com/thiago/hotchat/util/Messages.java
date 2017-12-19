package br.com.thiago.hotchat.util;

import java.util.ResourceBundle;

public class Messages {

	public static String emailRegistered() {
		return getMessage("user.email.registered");
	}

	public static String getMessage(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle");
		return bundle.getString(key);
	}

}
