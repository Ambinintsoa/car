package com.commercial.commerce.chat.controller;

import com.commercial.commerce.chat.service.MessageService;
import com.commercial.commerce.chat.service.WSService;
import com.commercial.commerce.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

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
        service.notifyUser(message.getReceiverEmail(), message);
        messageService.save(message);

        return ResponseEntity.ok("message sent");
    }

    @GetMapping("/contact/{id}")
    public HashMap<String, Message> getContact(@PathVariable String id) {
        return messageService.getContact(id);
    }

}
