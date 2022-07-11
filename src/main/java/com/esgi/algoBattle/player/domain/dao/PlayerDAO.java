package com.esgi.algoBattle.player.domain.dao;

import com.esgi.algoBattle.player.domain.model.Player;

import java.util.List;

public interface PlayerDAO {
    void create(Player player);

    void update(Player player);

    void delete(Long gameId, Long userId);

    Player findByIds(Long gameId, Long userId);

    List<Player> findAllByGame(Long gameId);

    List<Player> findAllByUser(Long userId);

    List<Player> findAllByAllGames(List<Long> gameIds);
}
