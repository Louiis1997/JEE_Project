package com.esgi.algoBattle.player.infrastructure.dataprovider.repository;

import com.esgi.algoBattle.player.infrastructure.dataprovider.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByGameIdAndUserId(Long gameId, Long userId);

    List<PlayerEntity> findByUserId(Long userId);

    List<PlayerEntity> findByGameId(Long gameId);

    List<PlayerEntity> findByGameIdIn(List<Long> gameIds);
}
