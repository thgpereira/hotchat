package br.com.thiago.hotchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.repository.UserRepository;
import br.com.thiago.hotchat.service.UserService;
import br.com.thiago.hotchat.service.exception.HotChatException;
import br.com.thiago.hotchat.util.Messages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Override
	public User save(User newUser) throws HotChatException {
		User user = userRepository.findByEmail(newUser.getEmail());
		if (user != null) {
			throw new HotChatException(Messages.emailRegistered());
		}

		newUser.setPassword(bcrypt.encode(newUser.getPassword()));
		return userRepository.saveAndFlush(newUser);
	}

}
