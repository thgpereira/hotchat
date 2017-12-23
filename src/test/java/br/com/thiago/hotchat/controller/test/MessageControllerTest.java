package br.com.thiago.hotchat.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.thiago.hotchat.controller.MessageController;
import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.service.MessageService;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(secure = false)
public class MessageControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private MessageService messageService;

	@Mock
	private Authentication authentication;

	@Mock
	private SecurityContext securityContext;

	@Before
	public void configSecurity() {
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	public void findHistoryReturnSucess() throws Exception {
		when(messageService.findMessagesHistory(Mockito.anyObject())).thenReturn(new ArrayList<MessageDTO>());
		mvc.perform(post("/messages/history").param("userEmailFrom", "unitteste@email.com.br")
				.param("userEmailTo", "unitteste@email.com.br").param("start", "01/01/2017").param("end", "01/01/2017")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk());
	}

}
