package com.esgi.algoBattle.resolution.infrastructure.web.response;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class ResolutionsByAlgoResponse implements Serializable {
    private Long userId;
    private Long gameId;
    private Long resolutionTime;
    private Boolean solved;
    private List<LinterError> linterErrors;
}
