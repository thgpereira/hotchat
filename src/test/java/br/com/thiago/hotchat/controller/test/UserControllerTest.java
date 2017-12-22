package br.com.thiago.hotchat.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

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

import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.controller.UserController;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.service.UserService;
import br.com.thiago.hotchat.util.Messages;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

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
	public void createUserReturnSucess() throws Exception {
		User userMock = new UserBuilder().build();
		User userMockSave = new UserBuilder().withId(1L).build();
		when(userService.save(userMock)).thenReturn(userMockSave);
		mvc.perform(post("/user/create").param("name", userMock.getName()).param("email", userMock.getEmail())
				.param("password", userMock.getPassword()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk());
	}

	@Test
	public void createUserReturnErrorValidation() throws Exception {
		User userMock = new UserBuilder().build();
		when(userService.save(userMock)).thenThrow(new HotChatException(Messages.emailRegistered()));
		mvc.perform(post("/user/create").param("name", userMock.getName()).param("email", userMock.getEmail())
				.param("password", userMock.getPassword()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void getLoadUserLoggedReturnSucess() throws Exception {
		User userMockSave = new UserBuilder().withId(1L).build();
		when(userService.findByEmail(Mockito.anyString())).thenReturn(userMockSave);
		mvc.perform(post("/user/user/load").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void getAllUsersReturnSucess() throws Exception {
		List<User> usersMockSave = Arrays.asList(new UserBuilder().withId(1L).build());
		when(userService.findAllUsersExcludeEmail(Mockito.anyString())).thenReturn(usersMockSave);
		mvc.perform(post("/user/users/listall").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
