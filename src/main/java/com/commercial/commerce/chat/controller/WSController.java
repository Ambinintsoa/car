package com.commercial.commerce.chat.controller;

import com.commercial.commerce.chat.service.WSService;
import com.commercial.commerce.chat.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WSController {

    @Autowired
    private WSService service;

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable final String id,
            @RequestBody final Message message) {
        System.out.println(message.toString());
        service.notifyUser(id, message);
    }
}
