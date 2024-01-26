package com.websocket.chat.repository;

import com.websocket.chat.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {
    List<Message> findByIdSenderAndIdReceiver(String idSender, String idReceiver);

    List<Message> findByIdReceiverAndIdSender(String idReceiver, String idSender);

}
