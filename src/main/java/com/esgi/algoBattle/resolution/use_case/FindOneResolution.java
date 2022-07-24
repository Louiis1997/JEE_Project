package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOneResolution {

    private final ResolutionDAO resolutionDAO;

    public Resolution execute(Long userId, Long algorithmId) {
        return findResolution(userId, algorithmId);
    }

    private Resolution findResolution(Long userId, Long algorithmId) {
        final var resolution = resolutionDAO.findByIds(userId, algorithmId);
        if (resolution == null) {
            throw new NotFoundException(String.format("Resolution with user %s and algo %s not found",
                    userId, algorithmId));
        }
        return resolution;
    }

}
