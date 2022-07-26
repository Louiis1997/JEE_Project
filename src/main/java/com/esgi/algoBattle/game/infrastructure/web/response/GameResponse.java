package com.esgi.algoBattle.game.infrastructure.web.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class GameResponse implements Serializable {
    private Long id;
    private LocalDateTime date;
    private Boolean over;

    public GameResponse() {
        id = null;
        date = null;
        over = null;
    }
}
