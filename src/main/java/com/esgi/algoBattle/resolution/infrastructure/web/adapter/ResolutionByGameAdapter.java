package com.esgi.algoBattle.resolution.infrastructure.web.adapter;

import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.web.response.ResolutionsByGameResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResolutionByGameAdapter {
    public ResolutionsByGameResponse toResponse(Resolution resolution) {
        return new ResolutionsByGameResponse()
                .setAlgoId(resolution.getAlgorithm().getId())
                .setUserId(resolution.getUser().getId())
                .setResolutionTime(resolution.getResolutionTime())
                .setSolved(resolution.getSolved())
                .setLinterErrors(resolution.getLinterErrors());
    }

    public List<ResolutionsByGameResponse> toResponses(List<Resolution> resolutions) {
        return resolutions
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
