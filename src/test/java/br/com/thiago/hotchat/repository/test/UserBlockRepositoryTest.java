package br.com.thiago.hotchat.repository.test;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.thiago.hotchat.builder.UserBlockBuilder;
import br.com.thiago.hotchat.builder.UserBuilder;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.entity.UserBlock;
import br.com.thiago.hotchat.repository.UserBlockRepository;

@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserBlockRepositoryTest {

	@Autowired
	private UserBlockRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void findUserBlockSuccess() {
		User userMock1 = new UserBuilder().build();
		User userMock2 = new UserBuilder().withEmail("unitteste1@email.com.br").build();
		User userFrom = entityManager.persist(userMock1);
		User userTo = entityManager.persist(userMock2);
		UserBlock userBlock = new UserBlockBuilder().withUserFrom(userFrom).withUserTo(userTo).build();
		entityManager.persist(userBlock);
		UserBlock userBlockCheck = repository.findByUserFromAndUserTo(userFrom, userTo);
		assertNotNull(userBlockCheck);
	}

}
