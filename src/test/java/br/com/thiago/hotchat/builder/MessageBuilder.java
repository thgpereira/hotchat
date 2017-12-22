package br.com.thiago.hotchat.builder;

import java.util.Date;

import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;

public class MessageBuilder {

	private Long id = null;
	private User userFrom = new UserBuilder().withId(1L).build();
	private User userTo = new UserBuilder().withId(2L).build();
	private String content = "Mensagem de teste";
	private MessageStatus messageStatus = MessageStatus.READ;
	private Date date = null;

	public Message build() {
		return new Message(id, userFrom, userTo, content, messageStatus, date, "unitteste@email.com.br",
				"unitteste@email.com.br");
	}

	public MessageBuilder withId(Long id) {
		this.id = id;
		return this;
	}

}
