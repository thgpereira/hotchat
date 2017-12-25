package br.com.thiago.hotchat.service;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.entity.UserBlock;
import br.com.thiago.hotchat.exception.HotChatException;

public interface UserBlockService {

	UserBlock save(UserBlock userBlock);

	void controlUserBlock(UserBlock userBlock) throws HotChatException;

	UserBlock findUserBlock(User userFrom, User userTo);

}
