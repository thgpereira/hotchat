package br.com.thiago.hotchat.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.dto.UserDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.entity.UserBlock;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.service.MessageService;
import br.com.thiago.hotchat.service.UserBlockService;
import br.com.thiago.hotchat.service.UserService;
import br.com.thiago.hotchat.util.Messages;
import br.com.thiago.hotchat.util.Url;

@Controller
@Transactional
public class ChatController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserBlockService userBlockService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping(Url.SEND_MESSAGE)
	@SendTo(Url.CHANNEL_MESSAGE_USER_PARAM)
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
		simpMessagingTemplate.convertAndSend(Url.CHANNEL_MESSAGE_USER + message.getUserEmailTo(), messageDTO);
	}

	@MessageMapping(Url.ADD_USER)
	public void addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
		User user = userService.findByEmail(message.getUserEmailFrom());
		user.setOnline(true);
		userService.update(user);
		headerAccessor.getSessionAttributes().put("email", user.getEmail());
		updateListContacts();
		getMessagesOffline(user.getEmail());
	}

	@MessageMapping(Url.CHAT_CONTACTS_LIST)
	@SendTo(Url.CHANNEL_CHAT_CONTACTS_LIST)
	public void updateListContacts() {
		List<UserDTO> usersDTO = userService.findAllUsersConvertDTO();
		simpMessagingTemplate.convertAndSend(Url.CHANNEL_CHAT_CONTACTS_LIST, usersDTO);
	}

	@MessageMapping(Url.CHAT_BLOCK_CONTACT)
	@SendTo(Url.CHANNEL_BLOCK_USER_PARAM)
	public void blockUser(@Payload UserBlock userBlock) {
		User userTo = userService.findByEmail(userBlock.getUserTo().getEmail());
		User userFrom = userService.findByEmail(userBlock.getUserFrom().getEmail());
		try {
			userBlock.setUserFrom(userFrom);
			userBlock.setUserTo(userTo);
			userBlockService.controlUserBlock(userBlock);
			simpMessagingTemplate.convertAndSend(Url.CHANNEL_BLOCK_USER + userFrom.getEmail(),
					Messages.processSuccess());
			updateListContacts();
		} catch (HotChatException e) {
			simpMessagingTemplate.convertAndSend(Url.CHANNEL_BLOCK_USER + userFrom.getEmail(), e.getMessage());
		}
	}

	private void getMessagesOffline(String emailUser) {
		User userTo = userService.findByEmail(emailUser);
		List<MessageDTO> messagesDTO = messageService.findMessagesPedentByUserToConvertDTO(userTo);
		if (!CollectionUtils.isEmpty(messagesDTO)) {
			simpMessagingTemplate.convertAndSend(Url.CHANNEL_MESSAGES_OFFLINE + userTo.getEmail(), messagesDTO);
			updateMessagesToRead(messagesDTO);
		}
	}

	private void updateMessagesToRead(List<MessageDTO> messagesDTO) {
		List<Long> ids = messagesDTO.stream().map(MessageDTO::getId).collect(Collectors.toList());
		messageService.updateMessagesPendentToRead(ids);
	}

}
