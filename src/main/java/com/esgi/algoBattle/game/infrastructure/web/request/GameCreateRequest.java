package com.esgi.algoBattle.game.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class GameCreateRequest {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotNull
    private Boolean over;
}
