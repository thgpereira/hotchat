package br.com.thiago.hotchat.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.builder.MessageBuilder;
import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.enumerator.MessageStatus;
import br.com.thiago.hotchat.repository.MessageRepository;
import br.com.thiago.hotchat.service.MessageService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceTest {

	@Autowired
	private MessageService messageService;

	@MockBean
	private MessageRepository messageRepository;

	@Test
	public void saveNewMessageSucess() {
		Message messageMock = new MessageBuilder().build();
		mockRepositorySave(messageMock);

		ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

		Message message = messageService.save(messageMock);
		assertNotNull(message);

		verify(messageRepository).saveAndFlush(messageCaptor.capture());

		Message messageBeforeSave = messageCaptor.getValue();
		assertEquals(messageBeforeSave.getUserFrom(), new UserBuilder().withId(1L).build());
		assertEquals(messageBeforeSave.getUserTo(), new UserBuilder().withId(2L).build());
		assertEquals(messageBeforeSave.getContent(), "Mensagem de teste");
		assertEquals(messageBeforeSave.getMessageStatus(), MessageStatus.READ);
		assertNotNull(messageBeforeSave.getDate());
	}

	public void mockRepositorySave(Message messageMock) {
		Message messageMockSave = new MessageBuilder().withId(1L).build();
		when(messageRepository.saveAndFlush(messageMock)).thenReturn(messageMockSave);
	}

}
