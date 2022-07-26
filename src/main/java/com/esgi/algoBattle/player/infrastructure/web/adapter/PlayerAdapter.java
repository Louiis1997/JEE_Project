package com.esgi.algoBattle.player.infrastructure.web.adapter;

import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.web.response.PlayerResponse;
import com.esgi.algoBattle.user.infrastructure.web.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PlayerAdapter {

    private final UserAdapter userAdapter;

    public PlayerResponse toResponse(Player player) {
        return new PlayerResponse()
                .setGame(player.getGame())
                .setUser(userAdapter.toResponse(player.getUser()))
                .setRemainingHealthPoints(player.getRemainingHealthPoints())
                .setWon(player.hasWon());
    }

    public List<PlayerResponse> toResponses(List<Player> players) {
        return players
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
