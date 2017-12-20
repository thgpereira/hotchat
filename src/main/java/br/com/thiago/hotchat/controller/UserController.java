package br.com.thiago.hotchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "Cadastra um usuário", notes = "Serviço REST responsável pelo cadastro de um usuário.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "Nome do usuário", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "email", value = "E-mail do usuário", required = true, dataType = "string", paramType = "form"),
			@ApiImplicitParam(name = "password", value = "Senha do usuário", required = true, dataType = "string", paramType = "form") })
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
