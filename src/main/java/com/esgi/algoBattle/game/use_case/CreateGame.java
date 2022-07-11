package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateGame {

    private final GameDAO gameDAO;

    public Game execute(LocalDateTime date, Boolean over) {
        Game game = new Game()
                .setDate(date)
                .setOver(over);

        return gameDAO.create(game);
    }
}
