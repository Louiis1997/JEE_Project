package com.esgi.algoBattle.game.infrastructure.dataprovider.repository;

import com.esgi.algoBattle.game.infrastructure.dataprovider.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
    List<GameEntity> findByIsOver(Boolean over);
}
