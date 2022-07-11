package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievePlayersByGames {

    private final PlayerDAO playerDAO;

    public List<Player> execute(List<Long> gameIds) {
        return playerDAO.findAllByAllGames(gameIds);
    }
}
