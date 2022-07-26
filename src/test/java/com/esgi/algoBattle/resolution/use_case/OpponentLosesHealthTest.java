package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.exception.GameOverException;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
public class OpponentLosesHealthTest {

    private final int playersDefaultRemainingHealthPoints = 100;
    @Autowired
    private OpponentLosesHealth opponentLosesHealth;
    @MockBean
    private GameDAO gameDAO;
    @MockBean
    private PlayerDAO playerDAO;
    private Game game;
    private Player playerEntity1;
    private Player playerEntity2;
    private List<Player> players;

    @BeforeEach
    public void initEach() {
        game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);

        var userEntity1 = new User();
        userEntity1.setId(1L);
        userEntity1.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");
        playerEntity1 = new Player();
        playerEntity1
                .setGame(game)
                .setUser(userEntity1)
                .setRemainingHealthPoints(playersDefaultRemainingHealthPoints)
                .setWon(false);

        var userEntity2 = new User();
        userEntity2.setId(2L);
        userEntity2.setEmail("john-doe@gmail.com").setName("Joh ").setPassword("");
        playerEntity2 = new Player();
        playerEntity2
                .setGame(game)
                .setUser(userEntity2)
                .setRemainingHealthPoints(playersDefaultRemainingHealthPoints)
                .setWon(false);

        players = List.of(playerEntity1, playerEntity2);
    }

    @Test
    public void opponent_should_lose_health_when_he_is_attacked() {
        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        opponentLosesHealth.execute(game.getId(), playerEntity2.getUser().getId(), 0);

        assertEquals(Integer.valueOf(playersDefaultRemainingHealthPoints - 10), playerEntity1.getRemainingHealthPoints());
    }

    @Test
    public void opponent_should_lose_health_when_he_is_attacked_subtracted_by_user_linter_errors() {
        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        var errorNumber = 2;
        var expectedOpponentRemainingHealthPoints = playersDefaultRemainingHealthPoints - (OpponentLosesHealth.DEFAULT_DAMAGE_AMOUNT - (int) (10.0 * ((float) (errorNumber) * 10.0 / 100.0)));

        opponentLosesHealth.execute(game.getId(), playerEntity2.getUser().getId(), errorNumber);
        assertEquals(expectedOpponentRemainingHealthPoints, playerEntity1.getRemainingHealthPoints());
    }

    @Test
    public void when_opponent_loses_health_should_throw_exception_if_game_is_over() {
        game.setOver(true);
        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        assertThrows(GameOverException.class, () -> opponentLosesHealth.execute(game.getId(), playerEntity2.getUser().getId(), 0));
    }
}
