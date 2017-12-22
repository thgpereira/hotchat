package br.com.thiago.hotchat.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.service.MessageService;
import br.com.thiago.hotchat.service.UserService;

@Controller
public class ChatController {

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat.sendMessage")
	@SendTo("/channel/user/{email}")
	public void sendMessage(@Payload Message message) {
		User userTo = userService.findByEmail(message.getUserEmailTo());
		User userFrom = userService.findByEmail(message.getUserEmailFrom());
		if (userTo.isOnline()) {
			message.setMessageStatus(MessageStatus.READ);
		} else {
			message.setMessageStatus(MessageStatus.PENDENT);
		}
		message.setUserFrom(userFrom);
		message.setUserTo(userTo);
		messageService.save(message);
		MessageDTO messageDTO = new MessageDTO();
		BeanUtils.copyProperties(message, messageDTO);
		messageDTO.setIdUserFrom(userFrom.getId());
		simpMessagingTemplate.convertAndSend("/channel/user/" + message.getUserEmailTo(), messageDTO);
	}

	@MessageMapping("/chat.addUser")
	public void addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
		User user = userService.findByEmail(message.getUserEmailFrom());
		user.setOnline(true);
		userService.update(user);
		headerAccessor.getSessionAttributes().put("email", user.getEmail());
	}

}
