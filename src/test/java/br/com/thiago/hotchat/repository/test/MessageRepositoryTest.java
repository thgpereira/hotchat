package br.com.thiago.hotchat.repository.test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.builder.MessageBuilder;
import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.repository.MessageRepository;

@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MessageRepositoryTest {

	@Autowired
	private MessageRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void findMessagesByStatusSucess() {
		User userFrom = entityManager.persist(new UserBuilder().build());
		User userTo = entityManager.persist(new UserBuilder().withEmail("unitteste2@email.com.br").build());
		createMessage(MessageStatus.READ, userFrom, userTo);
		createMessage(MessageStatus.READ, userFrom, userTo);
		createMessage(MessageStatus.PENDENT, userFrom, userTo);
		createMessage(MessageStatus.PENDENT, userFrom, userTo);
		createMessage(MessageStatus.PENDENT, userFrom, userTo);
		createMessage(MessageStatus.PENDENT, userTo, userFrom);
		List<Message> messagesPendent = repository.findByUserToAndMessageStatusOrderByUserToAscDateAsc(userTo,
				MessageStatus.PENDENT);
		assertThat(messagesPendent, hasSize(3));
		List<MessageStatus> status = messagesPendent.stream().map(m -> m.getMessageStatus())
				.collect(Collectors.toList());
		assertThat(status, not(hasItem(MessageStatus.READ)));
	}

	@Test
	public void updateStatusSucess() {
		User userFrom = entityManager.persist(new UserBuilder().build());
		User userTo = entityManager.persist(new UserBuilder().withEmail("unitteste2@email.com.br").build());
		List<Long> ids = Arrays.asList(createMessage(MessageStatus.PENDENT, userFrom, userTo).getId(),
				createMessage(MessageStatus.PENDENT, userFrom, userTo).getId(),
				createMessage(MessageStatus.PENDENT, userFrom, userTo).getId(),
				createMessage(MessageStatus.PENDENT, userTo, userFrom).getId());
		repository.updateMessagesRead(MessageStatus.READ, ids);
		List<Message> messagesPendent = repository.findByUserToAndMessageStatusOrderByUserToAscDateAsc(userTo,
				MessageStatus.PENDENT);
		assertThat(messagesPendent, hasSize(0));
	}

	private Message createMessage(MessageStatus messageStatus, User userFrom, User userTo) {
		Message message = new MessageBuilder().withStatus(messageStatus).withUserFrom(userFrom).withUserTo(userTo)
				.addDateTody().build();
		return entityManager.persist(message);
	}

}
