package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.user.domain.dao.UserDAO;

import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemovePlayer {

    private final PlayerDAO playerDAO;
    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    public void execute(Long gameId, Long userId) {
        checkIfGameExistsAndIsOpen(gameId);
        checkIfUserExists(userId);
        checkIfPlayerExists(gameId, userId);
        playerDAO.delete(gameId, userId);
    }

    private void checkIfGameExistsAndIsOpen(Long id) {
        Game game = gameDAO.findById(id);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
        if (game.isOver()) {
            throw new GameOverException(String.format("Game with id %s is closed", id));
        }
    }

    private void checkIfUserExists(Long id) {
        User exist = userDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
    }

    private void checkIfPlayerExists(Long gameId, Long userId) {
        Player exist = playerDAO.findByIds(gameId, userId);
        if (exist == null) {
            throw new NotFoundException(String.format("No relation between game %s and user %s", gameId, userId));
        }
    }
}
