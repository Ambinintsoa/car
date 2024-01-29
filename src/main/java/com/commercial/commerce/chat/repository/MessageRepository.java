package com.commercial.commerce.chat.repository;

import com.commercial.commerce.chat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderIdAndReceiverEmail(String SenderId, String receiverEmail);

    List<Message> findByReceiverEmailAndSenderId(String receiverEmail, String SenderId);

    List<Message> findByReceiverEmail(String receiverEmail);

}
