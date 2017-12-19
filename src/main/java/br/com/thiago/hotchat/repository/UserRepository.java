package br.com.thiago.hotchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.hotchat.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
