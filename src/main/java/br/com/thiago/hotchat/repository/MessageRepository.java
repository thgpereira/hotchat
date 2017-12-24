package br.com.thiago.hotchat.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.thiago.hotchat.dto.MessageDTO;
import br.com.thiago.hotchat.entity.Message;
import br.com.thiago.hotchat.entity.User;
import br.com.thiago.hotchat.enumerator.MessageStatus;

public interface MessageRepository extends JpaRepository<Message, Long> {

	List<Message> findByUserToAndMessageStatusOrderByUserToAscDateAsc(User userTo, MessageStatus messageStatus);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Message m SET m.messageStatus = :status WHERE m.id IN (:ids)")
	void updateMessagesRead(@Param("status") MessageStatus status, @Param("ids") List<Long> ids);

	@Query("SELECT NEW br.com.thiago.hotchat.dto.MessageDTO(m.id, uf.id, uf.email, m.content, m.date) FROM Message m "
			+ "JOIN m.userFrom uf WHERE ((uf = :userFrom AND m.userTo = :userTo) OR(uf=:userTo AND m.userTo=:userFrom))AND "
			+ "m.date BETWEEN :dateStart AND :dateEnd ORDER BY m.date")
	List<MessageDTO> findByUserFromAndUserToAndDateBetween(@Param("userFrom") User userFrom,
			@Param("userTo") User userTo, @Param("dateStart") Date start, @Param("dateEnd") Date end);

}
