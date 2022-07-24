package com.esgi.algoBattle.resolution.infrastructure.dataprovider.repository;


import com.esgi.algoBattle.resolution.infrastructure.dataprovider.entity.ResolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResolutionRepository extends JpaRepository<ResolutionEntity, Long> {
    Optional<ResolutionEntity> findByUserIdAndAlgoId(Long userId, Long algoId);

    List<ResolutionEntity> findByAlgoId(Long algoId);

    List<ResolutionEntity> findByUserId(Long userId);

    List<ResolutionEntity> findByGameId(Long gameId);
}
