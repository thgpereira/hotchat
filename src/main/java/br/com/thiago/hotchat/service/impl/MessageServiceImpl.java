package br.com.thiago.hotchat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.dto.MessageHistoryDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.repository.MessageRepository;
import br.com.thiago.hotchat.repository.UserRepository;
import br.com.thiago.hotchat.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Message save(Message newMessage) {
		newMessage.setDate(new Date());
		return messageRepository.saveAndFlush(newMessage);
	}

	@Override
	public List<MessageDTO> findMessagesPedentByUserToConvertDTO(User userTo) {
		List<Message> messages = messageRepository.findByUserToAndMessageStatusOrderByUserToAscDateAsc(userTo,
				MessageStatus.PENDENT);
		return convertListMessageToListDTO(messages);
	}

	@Override
	public void updateMessagesPendentToRead(List<Long> ids) {
		messageRepository.updateMessagesRead(MessageStatus.READ, ids);
	}

	@Override
	public List<MessageDTO> findMessagesHistory(MessageHistoryDTO messageHistoryDTO) {
		User userFrom = userRepository.findByEmail(messageHistoryDTO.getUserEmailFrom());
		User userTo = userRepository.findByEmail(messageHistoryDTO.getUserEmailTo());
		List<Message> messages = messageRepository.findByUserFromAndUserToAndDateBetween(userFrom, userTo,
				messageHistoryDTO.getDateStart(), messageHistoryDTO.getDateEnd());
		return convertListMessageToListDTO(messages);
	}

	private List<MessageDTO> convertListMessageToListDTO(List<Message> messages) {
		return messages.stream().map(m -> new MessageDTO(m.getId(), m.getUserFrom().getId(), m.getUserFrom().getEmail(),
				m.getContent(), m.getDate())).collect(Collectors.toList());
	}

}
