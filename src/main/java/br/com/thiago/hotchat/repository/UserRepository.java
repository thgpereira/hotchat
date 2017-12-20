package br.com.thiago.hotchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.thiago.hotchat.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Modifying
	@Query("UPDATE User u SET u.online = :online WHERE u.email = :email")
	void updateUserOnline(@Param("email") String email, @Param("online") boolean online);

}
