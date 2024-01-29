package com.commercial.commerce.chat.controller;

import com.commercial.commerce.chat.model.Message;
import com.commercial.commerce.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotifController {


    @Autowired
    private MessageService messageService;

    @GetMapping("/notification/{receiverEmail}")
    public List<Message> getNotif(@PathVariable String receiverEmail){
        Pageable pageable = PageRequest.of(0,10);
        return messageService.getNotif(receiverEmail,pageable);
    }
}
