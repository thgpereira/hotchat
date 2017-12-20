package br.com.thiago.hotchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "/create")
	public ResponseEntity<?> getCreate(User userRegister) {
		try {
			userService.save(userRegister);
			return (ResponseEntity<?>) ResponseEntity.ok(userRegister);
		} catch (HotChatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
