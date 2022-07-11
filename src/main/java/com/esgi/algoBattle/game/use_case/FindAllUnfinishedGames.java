package com.esgi.algoBattle.game.use_case;

import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUnfinishedGames {
    private final GameDAO gameDAO;

    public List<Game> execute() {
        return gameDAO.findAllUnfinishedGames();
    }
}
