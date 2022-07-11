package com.esgi.algoBattle.player.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@Accessors(chain = true)
public class PlayerRequest {
    @NotNull
    @PositiveOrZero
    @Max(100)
    private Integer remainingHealthPoints;

    @NotNull
    private Boolean won;
}
