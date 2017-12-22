package br.com.thiago.hotchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.hotchat.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
