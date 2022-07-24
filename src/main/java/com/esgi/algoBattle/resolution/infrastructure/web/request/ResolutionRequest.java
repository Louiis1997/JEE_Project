package com.esgi.algoBattle.resolution.infrastructure.web.request;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@Accessors(chain = true)
public class ResolutionRequest {
    @NotNull
    @Positive
    private Long algorithmId;

    @NotNull
    private Boolean solved;

    @NotNull
    private List<LinterError> linterErrors;
}
