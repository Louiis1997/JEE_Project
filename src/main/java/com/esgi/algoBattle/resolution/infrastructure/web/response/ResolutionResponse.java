package com.esgi.algoBattle.resolution.infrastructure.web.response;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.player.infrastructure.web.response.PlayerResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class ResolutionResponse implements Serializable {
    private Long gameId;
    private Long resolutionTime;
    private LocalDateTime startedTime;
    private Boolean solved;
    private PlayerResponse player;
    private List<LinterError> linterErrors;
}
