package com.esgi.algoBattle.player.infrastructure.web.adapter;

import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.web.response.GamesByUserResponse;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GamesByUserAdapter {
    public GamesByUserResponse toResponse(Player player) {
        return new GamesByUserResponse()
                .setGame(player.getGame())
                .setRemainingHealthPoints(player.getRemainingHealthPoints())
                .setWon(player.hasWon());
    }

    public List<GamesByUserResponse> toResponses(List<Player> players) {
        return players
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
