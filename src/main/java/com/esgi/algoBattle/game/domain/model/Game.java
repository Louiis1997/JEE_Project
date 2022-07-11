package com.esgi.algoBattle.game.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public class Game {
    private Long id;
    private LocalDateTime date;
    private Boolean over;

    public Boolean isOver() {
        return Boolean.TRUE.equals(over);
    }
}
