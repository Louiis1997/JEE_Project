package com.esgi.algoBattle.game.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class GameUpdateRequest {
    @NotNull
    private Boolean over;
}
