package br.com.thiago.hotchat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.repository.MessageRepository;
import br.com.thiago.hotchat.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Message save(Message newMessage) {
		newMessage.setDate(new Date());
		return messageRepository.saveAndFlush(newMessage);
	}

	@Override
	public List<MessageDTO> findMessagesPedentByUserToConvertDTO(User userTo) {
		List<Message> messages = messageRepository.findByUserToAndMessageStatusOrderByUserToAscDateAsc(userTo,
				MessageStatus.PENDENT);
		List<MessageDTO> messagesDTO = messages.stream().map(m -> new MessageDTO(m.getId(), m.getUserFrom().getId(),
				m.getUserFrom().getEmail(), m.getContent(), m.getDate())).collect(Collectors.toList());
		return messagesDTO;
	}

	@Override
	public void updateMessagesPendentToRead(List<Long> ids) {
		messageRepository.updateMessagesRead(MessageStatus.READ, ids);
	}

}
