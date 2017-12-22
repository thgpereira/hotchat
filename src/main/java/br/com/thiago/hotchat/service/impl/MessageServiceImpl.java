package br.com.thiago.hotchat.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.entity.Message;
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

}
