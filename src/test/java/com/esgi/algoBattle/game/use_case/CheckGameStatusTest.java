package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.dataprovider.repository.GameRepository;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.dataprovider.repository.PlayerRepository;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CheckGameStatusTest {

    private Game game;
    private Player playerEntity1;
    private Player playerEntity2;
    private List<Player> players;

    @Autowired
    private CheckGameStatus checkGameStatus;
    @MockBean
    private GameDAO gameDAO;
    @MockBean
    private PlayerDAO playerDAO;

    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private PlayerRepository playerRepository;

    @BeforeEach
    public void initEach() {
        game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);
        var userEntity1 = new User();
        userEntity1.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");

        playerEntity1 = new Player();
        playerEntity1
                .setGame(game)
                .setUser(userEntity1)
                .setRemainingHealthPoints(100)
                .setWon(false);

        var userEntity2 = new User();
        userEntity2.setEmail("john-doe@gmail.com").setName("Joh ").setPassword("");

        playerEntity2 = new Player();
        playerEntity2
                .setGame(game)
                .setUser(userEntity2)
                .setRemainingHealthPoints(100)
                .setWon(false);

        players = List.of(playerEntity1, playerEntity2);
    }

    @Test
    public void when_game_is_over_should_not_define_loser_winner_and_close_game() {
        game.setOver(true);

        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);

        checkGameStatus.execute(game.getId());

        verify(gameDAO, times(1)).findById(game.getId());
        verify(playerDAO, times(0)).findAllByGame(game.getId());
        verify(playerDAO, times(0)).update(playerEntity1);
        verify(playerDAO, times(0)).update(playerEntity2);
        verify(gameDAO, times(0)).update(game);
    }

    @Test
    public void when_game_is_not_over_and_loser_not_present_yet_should_get_game_players() {
        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        checkGameStatus.execute(game.getId());

        verify(gameDAO, times(1)).findById(game.getId());
        verify(playerDAO, times(1)).findAllByGame(game.getId());
        verify(playerDAO, times(0)).update(playerEntity1);
        verify(playerDAO, times(0)).update(playerEntity2);
        verify(gameDAO, times(0)).update(game);
    }

    @Test
    public void when_game_is_not_over_and_loser_present_should_end_game_and_define_winner() {
        playerEntity2.setRemainingHealthPoints(0);

        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);
        Mockito.when(gameDAO.update(game)).thenReturn(game);
        Mockito.doNothing().when(playerDAO).update(playerEntity1);
        Mockito.doNothing().when(playerDAO).update(playerEntity2);

        checkGameStatus.execute(game.getId());

        verify(gameDAO, times(1)).findById(game.getId());
        verify(playerDAO, times(1)).findAllByGame(game.getId());
        verify(playerDAO, times(1)).update(playerEntity1);
        verify(playerDAO, times(1)).update(playerEntity2);
        verify(gameDAO, times(1)).update(game);

        Assertions.assertEquals(playerEntity1.getWon(), true);
        Assertions.assertEquals(playerEntity2.getWon(), false);
        assertTrue(game.isOver());
    }
}