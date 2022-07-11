package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.exception.IncorrectPlayerNumberException;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CheckGameStatus {

    private final GameDAO gameDAO;
    private final PlayerDAO playerDAO;

    public void execute(Long gameId) {
        final var game = findGame(gameId);
        if (game.isOver()) return;

        final var players = findPlayers(gameId);
        final var loser = findLoser(players);

        if (loser.isPresent()) {
            defineLoser(loser.get());
            final var winner = findWinner(players);
            winner.ifPresent(this::defineWinner);
            closeTheGame(game);
        }
    }

    private void defineWinner(Player player) {
        player.setWon(true);
        playerDAO.update(player);
    }

    private void defineLoser(Player player) {
        player.setWon(false);
        playerDAO.update(player);
    }


    private void closeTheGame(Game game) {
        game.setOver(true);
        gameDAO.update(game);
    }

    private Optional<Player> findLoser(List<Player> players) {
        return players.stream()
                .filter(player -> player.getRemainingHealthPoints() <= 0)
                .findFirst();
    }

    private Optional<Player> findWinner(List<Player> players) {
        return players.stream()
                .filter(player -> player.getRemainingHealthPoints() > 0)
                .findFirst();
    }

    private Game findGame(Long gameId) {
        Game game = gameDAO.findById(gameId);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", gameId));
        }
        return game;
    }

    private List<Player> findPlayers(Long gameId) {
        final var players = playerDAO.findAllByGame(gameId);
        if (players.size() != 2) {
            throw new IncorrectPlayerNumberException("There should be 2 players in a game");
        }
        return players;
    }


}
