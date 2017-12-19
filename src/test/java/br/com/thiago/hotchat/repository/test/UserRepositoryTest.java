package br.com.thiago.hotchat.repository.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
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
		entityManager.persist(createUser());
		User user = repository.findByEmail(EMAIL);
		assertEquals(user.getName(), NAME);
		assertEquals(user.getEmail(), EMAIL);
		assertEquals(user.getPassword(), PASSWORD);
		assertEquals(user.isOnline(), false);
	}

	private User createUser() {
		User user = new User();
		user.setName(NAME);
		user.setEmail(EMAIL);
		user.setPassword(PASSWORD);
		return user;
	}

}
