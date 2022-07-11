package com.esgi.algoBattle.player.infrastructure.web.adapter;

import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.web.response.UsersByGameResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersByGameAdapter {
    public UsersByGameResponse toResponse(Player player) {
        return new UsersByGameResponse()
                .setUser(player.getUser())
                .setRemainingHealthPoints(player.getRemainingHealthPoints())
                .setWon(player.hasWon());
    }

    public List<UsersByGameResponse> toResponses(List<Player> players) {
        return players
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
