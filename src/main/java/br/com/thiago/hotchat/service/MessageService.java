package br.com.thiago.hotchat.service;

import java.util.List;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.dto.MessageHistoryDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;

public interface MessageService {

	Message save(Message newMessage);

	List<MessageDTO> findMessagesPedentByUserToConvertDTO(User userTo);

	void updateMessagesPendentToRead(List<Long> ids);

	List<MessageDTO> findMessagesHistory(MessageHistoryDTO messageHistoryDTO);

}
