package br.com.thiago.hotchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.entity.UserBlock;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

	UserBlock findByUserFromAndUserTo(User userFrom, User userTo);

}
