package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteResolution {

    private final ResolutionDAO resolutionDAO;
    private final UserDAO userDAO;
    private final AlgorithmDAO algorithmDAO;
    private final GameDAO gameDAO;

    public void execute(Long userId, Long algoId) {
        checkIfUserExists(userId);
        checkIfAlgoExists(algoId);
        var resolution = checkIfResolutionExists(userId, algoId);
        checkIfGameIsOpen(resolution.getGame().getId());
        resolutionDAO.delete(userId, algoId);
    }

    private void checkIfGameIsOpen(Long id) {
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

    private void checkIfAlgoExists(Long id) {
        var exist = algorithmDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("Algorithm with id %s not found", id));
        }
    }

    private Resolution checkIfResolutionExists(Long userId, Long algoId) {
        Resolution exist = resolutionDAO.findByIds(userId, algoId);
        if (exist == null) {
            throw new NotFoundException(String.format("No relation between user %s and algo %s", userId, algoId));
        }
        return exist;
    }
}
