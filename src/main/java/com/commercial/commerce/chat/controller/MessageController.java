package com.commercial.commerce.chat.controller;

import com.commercial.commerce.chat.model.Message;
import com.commercial.commerce.chat.model.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MessageController {

    @MessageMapping("/private-message/")
    @SendToUser("/topic/private-messages")
    public ResponseMessage getPrivateMessage(final Message message,
            final Principal principal) throws InterruptedException {
        Thread.sleep(200);
        return new ResponseMessage(HtmlUtils.htmlEscape(message.getContent()));
    }
}
