package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class RetrievePlayersByUserTest {

    @Autowired
    private RetrievePlayersByUser retrievePlayersByUser;

    @MockBean
    private PlayerDAO playerDAO;

    @MockBean
    private UserDAO userDAO;

    @Test
    void should_retrieve_players_by_userId() {
        Game game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);

        var userEntity = new User();
        userEntity.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");

        Player playerEntity = new Player()
                .setGame(game)
                .setUser(userEntity)
                .setRemainingHealthPoints(100)
                .setWon(false);

        List<Player> players = List.of(playerEntity);

        Mockito.when(userDAO.findById(userEntity.getId())).thenReturn(userEntity);

        Mockito.when(playerDAO.findAllByUser(userEntity.getId())).thenReturn(players);

        List<Player> foundPlayers = retrievePlayersByUser.execute(userEntity.getId());

        Assertions.assertEquals(players, foundPlayers);
    }

    @Test
    void should_throw_NotFoundException() {
        var userEntity = new User();
        userEntity.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");

        Mockito.when(userDAO.findById(userEntity.getId())).thenReturn(userEntity);

        Assertions.assertThrows(NotFoundException.class, () -> retrievePlayersByUser.execute(0L));
    }
}