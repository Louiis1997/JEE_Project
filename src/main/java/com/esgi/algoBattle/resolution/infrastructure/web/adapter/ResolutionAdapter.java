package com.esgi.algoBattle.resolution.infrastructure.web.adapter;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.web.response.ResolutionDetailsResponse;
import com.esgi.algoBattle.resolution.infrastructure.web.response.ResolutionResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResolutionAdapter {
    public ResolutionResponse toResponse(Resolution resolution) {
        return new ResolutionResponse()
                .setGameId(resolution.getGame().getId())
                .setResolutionTime(resolution.getResolutionTime())
                .setSolved(resolution.getSolved())
                .setStartedTime(resolution.getStartedTime())
                .setLinterErrors(resolution.getLinterErrors());
    }

    public List<ResolutionResponse> toResponses(List<Resolution> resolutions) {
        return resolutions
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ResolutionDetailsResponse> toDetailResponses(List<Resolution> resolutions, List<Player> players) {
        final var responses = new ArrayList<ResolutionDetailsResponse>();

        for (Resolution resolution : resolutions) {
            final var response = new ResolutionDetailsResponse();

            response.setAlgorithmName(resolution.getAlgorithm().getWording());
            response.setResolutionTime(resolution.getResolutionTime());
            response.setSolved(resolution.isSolved());
            response.setErrorNumber(resolution.getLinterErrors().stream().mapToLong(LinterError::getErrorNumber).sum());

            final var playersInGame = players.stream()
                    .filter(player -> player.getGame().getId().equals(resolution.getGame().getId()))
                    .collect(Collectors.toList());
            response.setPlayers(playersInGame);

            responses.add(response);
        }

        return responses;
    }
}
