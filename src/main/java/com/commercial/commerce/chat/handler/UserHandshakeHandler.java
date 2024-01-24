package com.commercial.commerce.chat.handler;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = LoggerFactory.getLogger(UserHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        String[] splittedUrl = request.getURI().toString().split("id_user_socket=");

        String randomId = splittedUrl[1];
        if (randomId.contains("&")) {
            randomId = randomId.split("&")[0];
        }
        // eto ilay mametraka id an'ilay mpandefa, miandry token :')
        System.out.println("URI = " + request.getPrincipal() + "     " + request.getURI());
        LOG.info("User with ID '{}' is connecting", randomId);

        return new UserPrincipal(randomId);
    }
}
