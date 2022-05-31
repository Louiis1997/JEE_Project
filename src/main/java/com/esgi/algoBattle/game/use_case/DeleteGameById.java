package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteGameById {

    private final GameDAO gameDAO;

    public void execute(Long id) {
        var game = gameDAO.findById(id);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
        gameDAO.delete(id);
    }
}
