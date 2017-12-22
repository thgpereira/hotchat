package br.com.thiago.hotchat.repository.test;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.repository.UserRepository;

@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	private static final String NAME = "Usuario Teste Unitário";
	private static final String EMAIL = "unitteste@email.com.br";
	private static final String PASSWORD = "123456";

	@Test
	public void findUserByEmail() {
		User userMock = new UserBuilder().build();
		entityManager.persist(userMock);
		User user = repository.findByEmail(EMAIL);
		assertEquals(user.getName(), NAME);
		assertEquals(user.getEmail(), EMAIL);
		assertEquals(user.getPassword(), PASSWORD);
		assertEquals(user.isOnline(), false);
	}

	@Test
	public void findUsersOnlineSuccess() {
		User userMock1 = new UserBuilder().build();
		User userMock2 = new UserBuilder().withEmail("unitteste1@email.com.br").build();
		User userMock3 = new UserBuilder().withEmail("unitteste2@email.com.br").build();
		entityManager.persist(userMock1);
		entityManager.persist(userMock2);
		entityManager.persist(userMock3);
		List<User> users = repository.findAllByOrderByOnlineDescNameAsc();
		List<String> emails = users.stream().map(u -> u.getEmail()).collect(Collectors.toList());
		assertThat(emails, hasItem("unitteste@email.com.br"));
		assertThat(emails, hasItem("unitteste1@email.com.br"));
		assertThat(emails, hasItem("unitteste2@email.com.br"));
	}

}
