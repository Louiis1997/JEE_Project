package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StartResolutionTest {

    private final Algorithm algorithm = new Algorithm();
    private final AlgorithmCase firstAlgorithmCase = new AlgorithmCase();
    private final CaseInput firstCaseInput = new CaseInput();
    @Autowired
    private StartResolution startResolution;
    @MockBean
    private UserDAO userDAO;
    @MockBean
    private AlgorithmDAO algorithmDAO;
    @MockBean
    private ResolutionDAO resolutionDAO;
    @MockBean
    private GameDAO gameDAO;
    @MockBean
    private PlayerDAO playerDAO;
    private Game game;
    private Player playerEntity1;
    private List<Player> players;

    @BeforeEach
    public void initEach() {
        game = new Game().setId(1L).setDate(LocalDateTime.now()).setOver(false);

        var userEntity1 = new User();
        userEntity1.setId(1L);
        userEntity1.setEmail("jean-dupont@gmail.com").setName("Jean").setPassword("");
        playerEntity1 = new Player();
        int playersDefaultRemainingHealthPoints = 100;
        playerEntity1
                .setGame(game)
                .setUser(userEntity1)
                .setRemainingHealthPoints(playersDefaultRemainingHealthPoints)
                .setWon(false);

        var userEntity2 = new User();
        userEntity2.setId(2L);
        userEntity2.setEmail("john-doe@gmail.com").setName("Joh ").setPassword("");
        Player playerEntity2 = new Player();
        playerEntity2
                .setGame(game)
                .setUser(userEntity2)
                .setRemainingHealthPoints(playersDefaultRemainingHealthPoints)
                .setWon(false);

        players = List.of(playerEntity1, playerEntity2);

        firstCaseInput.setId(1L);
        firstCaseInput.setValue("[5, 2, 2]");

        firstAlgorithmCase.setId(1L);
        firstAlgorithmCase.setAlgorithm(algorithm);
        firstAlgorithmCase.setName("Avec nombres positifs");
        firstAlgorithmCase.setOutputType("int");
        firstAlgorithmCase.setExpectedOutput("9");
        firstAlgorithmCase.setInput(new ArrayList<>() {
            {
                add(firstCaseInput);
            }
        });

        firstCaseInput.setAlgorithmCase(firstAlgorithmCase);

        algorithm.setId(1L);
        algorithm.setWording("Complétez la fonction sum afin de renvoyer la somme des nombres passés en paramètre");
        algorithm.setFuncName("sum");
        algorithm.setDescription("Addition de nombres");
        algorithm.setShortDescription(null);
        algorithm.setJavaInitialCode("public int sum(List<int> numbers) {}");
        algorithm.setPythonInitialCode("def sum(numbers):");
        algorithm.setCppInitialCode("int sum(int *numbers) {}");
        algorithm.setCases(new ArrayList<>() {
            {
                add(firstAlgorithmCase);
            }
        });
        algorithm.setTimeToSolve(180);
        algorithm.setTimeLimit(180);
        algorithm.setComplexity(1);
        algorithm.setMemoryLimit(400);
    }

    @Test
    public void when_start_resolutions_should_resolution_be_started() {
        var expectedResolution = new Resolution();
        expectedResolution.setUser(playerEntity1.getUser());
        expectedResolution.setAlgorithm(algorithm);
        expectedResolution.setGame(game);
        expectedResolution.setStartedTime(LocalDateTime.now());

        Mockito.when(userDAO.findById(playerEntity1.getUser().getId())).thenReturn(playerEntity1.getUser());
        Mockito.when(algorithmDAO.findById(algorithm.getId())).thenReturn(algorithm);
        Mockito.when(gameDAO.findById(game.getId())).thenReturn(game);
        Mockito.when(resolutionDAO.findByIds(playerEntity1.getUser().getId(), algorithm.getId())).thenReturn(null);
        Mockito.when(resolutionDAO.startResolution(any(Resolution.class))).thenReturn(expectedResolution);
        Mockito.when(playerDAO.findAllByGame(game.getId())).thenReturn(players);

        var startedResolution = startResolution.execute(playerEntity1.getUser().getId(), algorithm.getId(), game.getId());

        verify(userDAO, times(1)).findById(playerEntity1.getUser().getId());
        verify(algorithmDAO, times(1)).findById(algorithm.getId());
        verify(gameDAO, times(1)).findById(game.getId());
        verify(resolutionDAO, Mockito.times(1)).findByIds(playerEntity1.getUser().getId(), algorithm.getId());
        verify(playerDAO, times(1)).findAllByGame(game.getId());

        assertEquals(expectedResolution, startedResolution);
    }
}
