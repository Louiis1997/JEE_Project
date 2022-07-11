package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindGameById {

    private final GameDAO gameDAO;

    public Game execute(Long id) {
        var game = gameDAO.findById(id);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
        return game;
    }
}
