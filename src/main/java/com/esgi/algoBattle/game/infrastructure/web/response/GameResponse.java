package com.esgi.algoBattle.game.infrastructure.web.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class GameResponse implements Serializable {
    private Long id;
    private LocalDateTime date;
    private Boolean over;
}
