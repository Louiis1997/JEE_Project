package com.esgi.algoBattle.resolution.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.dataprovider.entity.ResolutionEntity;
import com.esgi.algoBattle.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ResolutionMapper {
    public ResolutionEntity toEntity(Resolution resolution) {
        return new ResolutionEntity()
                .setUserId(resolution.getUser().getId())
                .setAlgoId(resolution.getAlgorithm().getId())
                .setGameId(resolution.getGame().getId())
                .setResolutionTime(resolution.getResolutionTime())
                .setSolved(resolution.getSolved())
                .setStartedTime(resolution.getStartedTime())
                .setLinterErrors(resolution.getLinterErrors().stream()
                        .mapToInt(LinterError::getErrorNumber)
                        .sum());
    }

    public ResolutionEntity toStartedResolutionEntity(Resolution resolution) {
        return new ResolutionEntity()
                .setUserId(resolution.getUser().getId())
                .setAlgoId(resolution.getAlgorithm().getId())
                .setGameId(resolution.getGame().getId())
                .setStartedTime(resolution.getStartedTime());
    }

    public Resolution toDomain(ResolutionEntity entity) {
        return new Resolution()
                .setUser(new User().setId(entity.getUserId()))
                .setAlgorithm(new Algorithm().setId(entity.getAlgoId()))
                .setGame(new Game().setId(entity.getGameId()))
                .setResolutionTime(entity.getResolutionTime())
                .setStartedTime(entity.getStartedTime())
                .setLinterErrors(Collections.singletonList(new LinterError().setErrorNumber(entity.getLinterErrors())))
                .setSolved(entity.getSolved());
    }
}
