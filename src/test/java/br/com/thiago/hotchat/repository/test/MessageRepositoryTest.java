package br.com.thiago.hotchat.repository.test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
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
import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.repository.MessageRepository;
import br.com.thiago.hotchat.util.DateUtils;

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

	@Test
	public void findMessagesByHistorySuccess() throws ParseException {
		User userFrom = entityManager.persist(new UserBuilder().build());
		User userTo = entityManager.persist(new UserBuilder().withEmail("unitteste2@email.com.br").build());
		User userToNo = entityManager.persist(new UserBuilder().withEmail("unitteste3@email.com.br").build());
		Date start = DateUtils.formatFirstDateDay("01/01/2017");
		Date end = DateUtils.formatLastDateDay("01/01/2017");
		createMessage(MessageStatus.READ, userFrom, userTo, DateUtils.formatDate("01/01/2017 00:00:00"));
		createMessage(MessageStatus.READ, userFrom, userTo, DateUtils.formatDate("02/01/2017 01:23:23"));
		createMessage(MessageStatus.READ, userTo, userFrom, DateUtils.formatDate("01/01/2017 23:59:59"));
		createMessage(MessageStatus.READ, userFrom, userTo, DateUtils.formatDate("01/01/2017 23:59:59"));
		createMessage(MessageStatus.READ, userFrom, userToNo, DateUtils.formatDate("01/01/2017 13:39:57"));
		List<MessageDTO> messagesHistory = repository.findByUserFromAndUserToAndDateBetween(userFrom, userTo, start,
				end);
		assertThat(messagesHistory, hasSize(3));
	}

	private Message createMessage(MessageStatus messageStatus, User userFrom, User userTo) {
		return createMessage(messageStatus, userFrom, userTo, new Date());
	}

	private Message createMessage(MessageStatus messageStatus, User userFrom, User userTo, Date date) {
		Message message = new MessageBuilder().withStatus(messageStatus).withUserFrom(userFrom).withUserTo(userTo)
				.withDate(date).build();
		return entityManager.persist(message);
	}

}
