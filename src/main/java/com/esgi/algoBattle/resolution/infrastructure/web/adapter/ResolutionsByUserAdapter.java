package com.esgi.algoBattle.resolution.infrastructure.web.adapter;

import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.web.response.ResolutionsByUserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResolutionsByUserAdapter {
    public ResolutionsByUserResponse toResponse(Resolution resolution) {
        return new ResolutionsByUserResponse()
                .setAlgoId(resolution.getAlgorithm().getId())
                .setGameId(resolution.getGame().getId())
                .setResolutionTime(resolution.getResolutionTime())
                .setSolved(resolution.getSolved())
                .setLinterErrors(resolution.getLinterErrors());
    }

    public List<ResolutionsByUserResponse> toResponses(List<Resolution> resolutions) {
        return resolutions
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
