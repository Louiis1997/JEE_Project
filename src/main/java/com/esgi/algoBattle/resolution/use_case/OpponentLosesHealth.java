package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.exception.IncorrectPlayerNumberException;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.resolution.domain.exception.NoCurrentPlayerException;
import com.esgi.algoBattle.resolution.domain.exception.NoGameSpecifiedException;
import com.esgi.algoBattle.resolution.domain.exception.NoOpponentSpecifiedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpponentLosesHealth {

    private final PlayerDAO playerDAO;
    private final GameDAO gameDAO;

    private final Integer DEFAUMT_DAMAGE_AMOUNT = 10;

    public void execute(Long gameId, Long userId, Integer errorNumber) {
        final var game = findGame(gameId);
        assertGameIsNotOver(game);
        final var players = findPlayers(gameId);

        final var currentPlayer = getCurrentPlayer(players, userId);

        final var opponent = getOpponent(players, currentPlayer);
        attackOpponent(DEFAUMT_DAMAGE_AMOUNT, opponent, errorNumber);
    }

    private void assertGameIsNotOver(Game game) {
        if (game.isOver()) {
            throw new GameOverException(String.format("Game %s is over", game.getId()));
        }
    }

    private Player getOpponent(List<Player> players, Player currentPlayer) {
        final var opponent = players.stream()
                .filter(player -> !player.equals(currentPlayer))
                .findFirst();

        if (opponent.isEmpty()) {
            throw new NoOpponentSpecifiedException("No opponent to use item");
        }
        return opponent.get();
    }

    private List<Player> findPlayers(Long gameId) {
        final var players = playerDAO.findAllByGame(gameId);
        if (players.size() != 2) {
            throw new IncorrectPlayerNumberException("There should be 2 players in a game");
        }
        return players;
    }

    private Player getCurrentPlayer(List<Player> players, Long userId) {
        final var currentPlayer = players.stream()
                .filter(player -> player.getUser().getId().equals(userId))
                .findFirst();

        if (currentPlayer.isEmpty()) {
            throw new NoCurrentPlayerException("No performer to use item");
        }

        return currentPlayer.get();
    }

    private Game findGame(Long gameId) {
        if (gameId == null) {
            throw new NoGameSpecifiedException("No game specified to use the item");
        }
        final var game = gameDAO.findById(gameId);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", gameId));
        }
        return game;
    }

    private void attackOpponent(Integer defaultDamageAmount, Player target, Integer errorNumber) {
        var damageAmount = calculateDamageAmount(defaultDamageAmount, errorNumber);
        final var remainingHealthPoints = target.getRemainingHealthPoints();
        target.setRemainingHealthPoints(remainingHealthPoints - damageAmount);
        playerDAO.update(target);
    }

    public Integer calculateDamageAmount(Integer defaultDamageAmount, Integer errorNumber) {
        final var percentage = (double) errorNumber * 10 / 100;
        final var valueToRetire = defaultDamageAmount * percentage;
        return Math.max(1, defaultDamageAmount - (int) valueToRetire);
    }
}
