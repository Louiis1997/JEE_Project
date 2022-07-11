package com.esgi.algoBattle.game.infrastructure.web.adapter;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.web.response.GameResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameAdapter {
    public GameResponse toResponse(Game game) {
        return new GameResponse()
                .setId(game.getId())
                .setDate(game.getDate())
                .setOver(game.getOver());
    }

    public List<GameResponse> toResponses(List<Game> games) {
        return games
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
