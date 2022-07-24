package com.esgi.algoBattle.resolution.infrastructure.web.adapter;

import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.web.response.ResolutionsByAlgoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResolutionsByAlgoAdapter {
    public ResolutionsByAlgoResponse toResponse(Resolution resolution) {
        return new ResolutionsByAlgoResponse()
                .setUserId(resolution.getUser().getId())
                .setGameId(resolution.getGame().getId())
                .setResolutionTime(resolution.getResolutionTime())
                .setSolved(resolution.getSolved())
                .setLinterErrors(resolution.getLinterErrors());
    }

    public List<ResolutionsByAlgoResponse> toResponses(List<Resolution> resolutions) {
        return resolutions
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
