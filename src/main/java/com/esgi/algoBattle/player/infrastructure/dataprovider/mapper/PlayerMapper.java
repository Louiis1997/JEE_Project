package com.esgi.algoBattle.player.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.dataprovider.entity.PlayerEntity;
import com.esgi.algoBattle.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {
    public PlayerEntity toEntity(Player player) {
        return new PlayerEntity()
                .setGameId(player.getGame().getId())
                .setUserId(player.getUser().getId())
                .setRemainingHealthPoints(player.getRemainingHealthPoints())
                .setWon(player.hasWon());
    }

    public Player toDomain(PlayerEntity entity) {
        return new Player()
                .setUser(new User().setId(entity.getUserId()))
                .setGame(new Game().setId(entity.getGameId()))
                .setRemainingHealthPoints(entity.getRemainingHealthPoints())
                .setWon(entity.getWon());
    }

    public Player toDomainWithModels(PlayerEntity entity, Game game, User user) {
        return new Player()
                .setGame(game)
                .setUser(user)
                .setRemainingHealthPoints(entity.getRemainingHealthPoints())
                .setWon(entity.getWon());
    }
}
