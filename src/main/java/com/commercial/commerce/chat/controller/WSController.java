package com.commercial.commerce.chat.controller;

import com.commercial.commerce.chat.service.MessageService;
import com.commercial.commerce.chat.service.WSService;
import com.commercial.commerce.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class WSController {

    @Autowired
    private WSService service;

    @Autowired
    private MessageService messageService;

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
            @RequestBody final Message message) {
        System.out.println(message.toString());
        service.notifyUser(id, message);
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessage(
            @RequestBody Message message) {
        System.out.println(message.toString());
        message.setDate(new Date(System.currentTimeMillis()));
        service.notifyUser(message.getReceiverEmail(), message);
        messageService.save(message);

        return ResponseEntity.ok("message sent");
    }

    @GetMapping("/contact/{email}")
    public HashMap<String, Message> getContact(@PathVariable String email) {
        return messageService.getContact(email);
    }

    @PostMapping("/discussion")
    public List<Message> getDiscussion(@RequestBody String user1, @RequestBody String user2){
        return messageService.getDiscussion(user1,user2);
    }


}
