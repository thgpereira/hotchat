package br.com.thiago.hotchat.controller.test;

import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.builder.MessageBuilder;
import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.MessageService;
import br.com.thiago.hotchat.service.UserService;

@RunWith(SpringRunner.class)
@Ignore
public class ChatControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private MessageService messageService;

	@Test
	public void sendMessageReturnSucess() throws Exception {
		Message messageMock = new MessageBuilder().build();
		Message messageMockSave = new MessageBuilder().withId(1L).build();
		User userMockSave = new UserBuilder().withId(1L).build();
		when(userService.findByEmail(Mockito.anyString())).thenReturn(userMockSave);
		when(messageService.save(messageMock)).thenReturn(messageMockSave);

	}

}
