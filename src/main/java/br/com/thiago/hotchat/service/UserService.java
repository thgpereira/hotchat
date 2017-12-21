package br.com.thiago.hotchat.service;

import java.util.List;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.exception.HotChatException;

public interface UserService {

	User save(User newUser) throws HotChatException;

	void updateUserOnline(String email, boolean online);

	List<User> findAllUsersExcludeEmail(String emailExclude);

}
