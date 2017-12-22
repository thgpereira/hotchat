package br.com.thiago.hotchat.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.dto.UserDTO;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.Role;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.repository.UserRepository;
import br.com.thiago.hotchat.service.UserService;
import br.com.thiago.hotchat.util.Messages;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

	@Override
	public void updateUserOnline(String email, boolean online) {
		userRepository.updateUserOnline(email, online);
	}

	@Override
	public List<UserDTO> findAllUsersConvertDTO() {
		List<User> users = userRepository.findAllByOrderByOnlineDescNameAsc();
		List<UserDTO> usersDTO = users.stream()
				.map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), u.isOnline())).collect(Collectors.toList());
		return usersDTO;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User update(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getUserAuthority());
	}

	private List<SimpleGrantedAuthority> getUserAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority(Role.USER.toString()));
	}

}
