package br.com.thiago.hotchat.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.hotchat.dto.UserDTO;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Busca o usuário logado", notes = "Serviço REST responsável pelo busca do usuário logado.")
	@PostMapping(value = "/user/load")
	public ResponseEntity<?> getLoadUserLogged() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User userLogged = userService.findByEmail(auth.getName());
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userLogged, userDTO);
			return (ResponseEntity<?>) ResponseEntity.ok(userDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ApiOperation(value = "Lista os usuários cadastrados.", notes = "Serviço REST responsável pelo busca de todos os usuário cadastrados.")
	@PostMapping(value = "/users/listall")
	public ResponseEntity<?> getListAllUsers() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<User> users = userService.findAllUsersExcludeEmail(auth.getName());
			List<UserDTO> usersDTO = users.stream().map(u -> new UserDTO(u.getName(), u.getEmail(), u.isOnline()))
					.collect(Collectors.toList());
			return (ResponseEntity<?>) ResponseEntity.ok(usersDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
