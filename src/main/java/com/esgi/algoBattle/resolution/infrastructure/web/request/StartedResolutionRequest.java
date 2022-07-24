package com.esgi.algoBattle.resolution.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Accessors(chain = true)
public class StartedResolutionRequest {
    @NotNull
    @Positive
    private Long gameId;

    @NotNull
    @Positive
    private Long algorithmId;
}
