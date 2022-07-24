package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.game.domain.dao.GameDAO;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllResolutionsByGame {
    private final ResolutionDAO resolutionDAO;
    private final GameDAO gameDAO;

    public List<Resolution> execute(Long gameId) {
        checkIfGameExists(gameId);
        return resolutionDAO.findResolutionsByGame(gameId);
    }


    private void checkIfGameExists(Long id) {
        Game exist = gameDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("Game with id %s not found", id));
        }
    }
}
