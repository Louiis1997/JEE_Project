package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
class RetrievePlayersByGamesTest {

    @Autowired
    private RetrievePlayersByGame retrievePlayersByGame;

    @MockBean
    private PlayerDAO playerDAO;

    @MockBean
    private GameDAO gameDAO;

    @Test
    void should_retrieve_players_by_gameId() {
        Game game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);
        var userEntity1 = new User();
        userEntity1.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");

        Player playerEntity1 = new Player()
                .setGame(game)
                .setUser(userEntity1)
                .setRemainingHealthPoints(100)
                .setWon(false);

        var userEntity2 = new User();
        userEntity2.setEmail("john-doe@gmail.com").setName("Joh ").setPassword("");

        Player playerEntity2 = new Player()
                .setGame(game)
                .setUser(userEntity2)
                .setRemainingHealthPoints(100)
                .setWon(false);

        List<Player> players = List.of(playerEntity1, playerEntity2);

        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);

        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        List<Player> foundPlayers = retrievePlayersByGame.execute(game.getId());

        Assertions.assertEquals(players, foundPlayers);
    }

    @Test
    void should_throw_NotFoundException() {
        Game game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);

        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);

        Assertions.assertThrows(NotFoundException.class, () -> retrievePlayersByGame.execute(0L));
    }
}