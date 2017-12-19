package br.com.thiago.hotchat.service;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.exception.HotChatException;

public interface UserService {

	User save(User newUser) throws HotChatException;

}
