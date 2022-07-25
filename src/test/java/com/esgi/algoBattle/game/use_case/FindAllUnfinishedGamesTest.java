package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.dataprovider.entity.GameEntity;
import com.esgi.algoBattle.game.infrastructure.dataprovider.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class FindAllUnfinishedGamesTest {

    private Game game1;
    private Game game2;
    private GameEntity gameEntity1;
    private GameEntity gameEntity2;

    @Autowired
    private FindAllUnfinishedGames findAllUnfinishedGames;
    @MockBean
    private GameRepository gameRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void initEach() {
        game1 = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(true);
        game2 = new Game().setId(2L).setDate(LocalDateTime.now()).setOver(true);
        gameEntity1 = new GameEntity().setId(game1.getId()).setCreatedAt(game1.getDate()).setIsOver(game1.getOver());
        gameEntity2 = new GameEntity().setId(game2.getId()).setCreatedAt(game2.getDate()).setIsOver(game2.getOver());
    }

    @Test
    public void when_no_unfinished_games() {
        gameEntity1.setIsOver(true);
        gameEntity2.setIsOver(true);
        Mockito.when(gameRepository.findByIsOver(false)).thenReturn(List.of());

        var games = findAllUnfinishedGames.execute();

        Assertions.assertEquals(0, games.size());
    }

    @Test
    public void when_one_unfinished_game() {
        game1.setOver(false);
        game2.setOver(true);
        gameEntity1.setIsOver(false);
        gameEntity2.setIsOver(true);
        Mockito.when(gameRepository.findByIsOver(false)).thenReturn(List.of(gameEntity1));

        var games = findAllUnfinishedGames.execute();

        Assertions.assertEquals(1, games.size());
        Assertions.assertEquals(game1, games.get(0));
    }

    @Test
    public void when_two_unfinished_games() {
        game1.setOver(false);
        game2.setOver(false);
        gameEntity1.setIsOver(false);
        gameEntity2.setIsOver(false);
        Mockito.when(gameRepository.findByIsOver(false)).thenReturn(List.of(gameEntity1, gameEntity2));

        var games = findAllUnfinishedGames.execute();

        Assertions.assertEquals(2, games.size());
        Assertions.assertEquals(game1, games.get(0));
        Assertions.assertEquals(game2, games.get(1));
    }
}