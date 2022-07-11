package com.esgi.algoBattle.player.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.player.domain.dao.PlayerDAO;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetrievePlayersByUser {

    private final PlayerDAO playerDAO;
    private final UserDAO userDAO;

    public List<Player> execute(Long userId) {
        checkIfUserExists(userId);
        return playerDAO.findAllByUser(userId);
    }

    private void checkIfUserExists(Long id) {
        User exist = userDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
    }

}
