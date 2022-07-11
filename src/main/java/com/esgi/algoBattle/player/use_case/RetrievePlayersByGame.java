package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievePlayersByGame {

    private final PlayerDAO playerDAO;
    private final GameDAO gameDAO;

    public List<Player> execute(Long gameId) {
        checkIfGameExists(gameId);
        return playerDAO.findAllByGame(gameId);
    }

    private void checkIfGameExists(Long id) {
        Game exist = gameDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
    }
}
