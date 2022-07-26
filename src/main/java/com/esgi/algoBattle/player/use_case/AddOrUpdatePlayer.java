package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.player.domain.exception.TooManyPlayersInGameException;
import com.esgi.algoBattle.player.domain.exception.TooManyPlayersWonException;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.web.request.PlayerRequest;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddOrUpdatePlayer {

    private final PlayerDAO playerDAO;
    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    public Player execute(Long gameId, Long userId, PlayerRequest request) {
        Game game = findGameById(gameId);
        User user = findUserById(userId);

        var player = new Player()
                .setGame(game)
                .setUser(user)
                .setRemainingHealthPoints(request.getRemainingHealthPoints())
                .setWon(request.getWon());

        if (playerAlreadyExists(player)) {
            updatePlayer(player);
            if (player.hasWon()) {
                int currentLevel = user.getLevel();
                user.setLevel(currentLevel + 1);
            }
        } else {
            createPlayer(player);
        }

        return player;
    }

    private void updatePlayer(Player player) {
        var currentPlayers = getCurrentPlayersInGame(player.getGame());
        checkIfTooManyPlayersWon(player, currentPlayers);
        playerDAO.update(player);
    }

    private void createPlayer(Player player) {
        var currentPlayers = getCurrentPlayersInGame(player.getGame());
        checkIfGameIsFull(currentPlayers);
        checkIfTooManyPlayersWon(player, currentPlayers);
        playerDAO.create(player);
    }

    private void checkIfTooManyPlayersWon(Player player, List<Player> currentPlayers) {
        if (player.hasWon()) {
            if (currentPlayers.size() == 0) {
                throw new TooManyPlayersWonException("Player can not be alone in game and win it");
            }
            if (currentPlayers.size() == 1 && player.hasWon()) {
                throw new TooManyPlayersWonException("Player can not be alone in game and win it");
            }
            assertThatOtherPlayerLost(player.getUser().getId(), currentPlayers);
        }
    }

    private void assertThatOtherPlayerLost(Long currentUserId, List<Player> players) {
        if (players.stream()
                .anyMatch(player -> player.hasWon() &&
                        !player.getUser().getId().equals(currentUserId))) {
            throw new TooManyPlayersWonException("Both players can not win the game");
        }
    }

    private void checkIfGameIsFull(List<Player> players) {
        if (players.size() >= 2) {
            throw new TooManyPlayersInGameException("A game can not regroup more than 2 players");
        }
    }

    private List<Player> getCurrentPlayersInGame(Game game) {
        return playerDAO.findAllByGame(game.getId());
    }

    private Game findGameById(Long id) {
        var game = gameDAO.findById(id);
        if (game == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
        if (game.isOver()) {
            throw new GameOverException(String.format("Game with id %s is closed", id));
        }
        return game;
    }

    private User findUserById(Long id) {
        var user = userDAO.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
        return user;
    }

    private Boolean playerAlreadyExists(Player player) {
        return playerDAO.findByIds(player.getGame().getId(),
                player.getUser().getId()) != null;
    }
}
