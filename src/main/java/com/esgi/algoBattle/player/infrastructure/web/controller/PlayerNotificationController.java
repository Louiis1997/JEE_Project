package com.esgi.algoBattle.player.infrastructure.web.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerNotificationController {

    @MessageMapping("/games/{gameId}")
    @SendTo("/start/games/{gameId}")
    public int launchGameAfterOtherPlayerJoining(@DestinationVariable int gameId) {
        return gameId;
    }

}
