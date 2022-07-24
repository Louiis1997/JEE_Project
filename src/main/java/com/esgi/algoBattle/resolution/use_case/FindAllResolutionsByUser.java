package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllResolutionsByUser {

    private final ResolutionDAO resolutionDAO;
    private final UserDAO userDAO;
    private final AlgorithmDAO algorithmDAO;

    public List<Resolution> execute(Long userId) {
        checkIfUserExists(userId);
        final var resolutions = resolutionDAO.findResolutionsByUser(userId);

        for (Resolution resolution : resolutions) {
            final var algo = algorithmDAO.findById(resolution.getAlgorithm().getId());
            resolution.setAlgorithm(algo);
        }

        return resolutions;
    }

    private void checkIfUserExists(Long id) {
        User exist = userDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
    }
}
