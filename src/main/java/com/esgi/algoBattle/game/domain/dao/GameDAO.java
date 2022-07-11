package com.esgi.algoBattle.game.domain.dao;

import com.esgi.algoBattle.game.domain.model.Game;
import java.util.List;

public interface GameDAO {
    Game create(Game game);

    Game findById(Long id);

    List<Game> findAll();

    List<Game> findAllUnfinishedGames();

    Game update(Game game);

    void delete(Long id);
}
