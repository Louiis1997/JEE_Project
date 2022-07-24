package com.esgi.algoBattle.game.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.dataprovider.entity.GameEntity;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public Game toDomain(GameEntity entity) {
        return new Game()
                .setId(entity.getId())
                .setDate(entity.getCreatedAt())
                .setOver(entity.getIsOver());
    }

    public GameEntity toEntity(Game game) {
        return new GameEntity()
                .setCreatedAt(game.getDate())
                .setIsOver(game.getOver());
    }
}
