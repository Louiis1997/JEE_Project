package com.esgi.algoBattle.player.use_case;

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
class RetrievePlayersByGameTest {

    @Autowired
    private RetrievePlayersByGames retrievePlayersByGames;

    @MockBean
    private PlayerDAO playerDAO;

    @Test
    void should_retrieve_players_by_gameIds() {
        Game game1 = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);
        var userEntity1 = new User();
        userEntity1.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");

        Player playerEntity1 = new Player()
                .setGame(game1)
                .setUser(userEntity1)
                .setRemainingHealthPoints(100)
                .setWon(false);

        Game game2 = new Game().setId(2L).setDate(LocalDateTime.now()).setOver(false);
        var userEntity2 = new User();
        userEntity2.setEmail("john-doe@gmail.com").setName("Joh ").setPassword("");

        Player playerEntity2 = new Player()
                .setGame(game2)
                .setUser(userEntity2)
                .setRemainingHealthPoints(100)
                .setWon(false);

        List<Long> gameIds = List.of(game1.getId(), game2.getId());

        List<Player> players = List.of(playerEntity1, playerEntity2);

        Mockito.when(playerDAO.findAllByAllGames(gameIds)).thenReturn(players);

        List<Player> foundPlayers = retrievePlayersByGames.execute(gameIds);

        Assertions.assertEquals(players, foundPlayers);
    }
}