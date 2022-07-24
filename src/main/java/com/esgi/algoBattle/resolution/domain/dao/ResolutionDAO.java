package com.esgi.algoBattle.resolution.domain.dao;

import com.esgi.algoBattle.resolution.domain.model.Resolution;

import java.util.List;

public interface ResolutionDAO {
    Resolution create(Resolution resolution);

    Resolution startResolution(Resolution resolution);

    Resolution update(Resolution resolution);

    void delete(Long userId, Long algoId);

    Resolution findByIds(Long userId, Long algoId);

    List<Resolution> findResolutionsByUser(Long userId);

    List<Resolution> findResolutionsByAlgo(Long algoId);

    List<Resolution> findResolutionsByGame(Long gameId);
}
