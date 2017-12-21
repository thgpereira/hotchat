package br.com.thiago.hotchat.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(ChatControllerTest.class)
@AutoConfigureMockMvc(secure = false)
public class ChatControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@Test
	@Ignore
	public void getLoadUserLoggedReturnSucess() throws Exception {
		User userMock = new UserBuilder().build();
		User userMockSave = new UserBuilder().withId(1L).build();
		when(userService.findByEmail(userMock.getEmail())).thenReturn(userMockSave);
		mvc.perform(post("/chat/user/load").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
