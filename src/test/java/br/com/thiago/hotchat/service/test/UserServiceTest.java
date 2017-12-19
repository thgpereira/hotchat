package br.com.thiago.hotchat.service.test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.service.UserService;
import br.com.thiago.hotchat.service.exception.HotChatException;
import br.com.thiago.hotchat.util.Messages;

@RunWith(SpringRunner.class)
@DataJpaTest
@Ignore
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private static final String NAME = "Usuario Teste Unitário";
	private static final String EMAIL = "unitteste@email.com.br";
	private static final String PASSWORD = "123456";

	@Test
	public void saveNewUserSucess() throws HotChatException {
		User user = userService.save(createUser());
		assertEquals(user.getName(), NAME);
		assertEquals(user.getEmail(), EMAIL);
		assertEquals(user.getPassword(), bcrypt.encode(PASSWORD));
		assertEquals(user.isOnline(), false);
	}

	@Test
	public void saveNewUserErrorEmailRegistered() throws HotChatException {
		entityManager.persist(createUser());
		userService.save(createUser());
		expectedException.expect(HotChatException.class);
		expectedException.expectMessage(Messages.emailRegistered());
	}

	private User createUser() {
		User user = new User();
		user.setName(NAME);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		return user;
	}
}
