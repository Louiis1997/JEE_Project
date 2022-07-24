package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.exception.IncorrectPlayerNumberException;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaveResolution {

    private final ResolutionDAO resolutionDAO;
    private final UserDAO userDAO;
    private final AlgorithmDAO algorithmDAO;
    private final GameDAO gameDAO;
    private final PlayerDAO playerDAO;

    public Resolution execute(Long userId,
                              Long algorithmId,
                              Long gameId,
                              Long resolutionTime,
                              Boolean solved,
                              List<LinterError> linterErrors) {

        checkResolutionExists(userId, algorithmId);

        final var user = getUser(userId);
        final var algorithm = getAlgorithm(algorithmId);
        final var game = getGame(gameId);

        assertGameIsNotOver(game);
        assertPlayersAreTwo(game);

        final var newResolution =
                buildResolution(user, algorithm, game, resolutionTime, solved, linterErrors);
        return resolutionDAO.update(newResolution);
    }

    private Resolution buildResolution(User user, Algorithm algorithm,
                                       Game game,
                                       Long resolutionTime, Boolean solved,
                                       List<LinterError> linterErrors) {
        return new Resolution()
                .setUser(user)
                .setAlgorithm(algorithm)
                .setGame(game)
                .setResolutionTime(resolutionTime)
                .setSolved(solved)
                .setLinterErrors(linterErrors);
    }

    private Game getGame(Long id) {
        Game game = gameDAO.findById(id);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
        return game;
    }

    private void assertGameIsNotOver(Game game) {
        if (game.isOver()) {
            throw new GameOverException(String.format("Game with id %s is closed", game.getId()));
        }
    }

    private User getUser(Long id) {
        User exist = userDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
        return exist;
    }

    private Algorithm getAlgorithm(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("Algorithm with id %s not found", id));
        }
        return exist;
    }

    private void checkResolutionExists(Long userId, Long algorithmId) {
        final var resolution = resolutionDAO.findByIds(userId, algorithmId);
        if (resolution == null) {
            throw new NotFoundException(String.format("Resolution with user %s and algo %s not found",
                    userId, algorithmId));
        }
    }

    private void assertPlayersAreTwo(Game game) {
        if (playerDAO.findAllByGame(game.getId()).size() != 2) {
            throw new IncorrectPlayerNumberException("There should be two different players in the game");
        }
    }
}
