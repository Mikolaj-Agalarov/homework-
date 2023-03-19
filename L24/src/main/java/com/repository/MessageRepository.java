package com.repository;

import com.entity.Message;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId")
    List<Message> findAllMessagesBySenderAndReceiver(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

//    List<Message> findAllBySenderAndReceiver(User sender, User recipient);
//    @Query("select m from Message m where m.sender=: sender and m.receiver=: recipient")
//    List<Message> findAllBys(User sender, User recipient);

}