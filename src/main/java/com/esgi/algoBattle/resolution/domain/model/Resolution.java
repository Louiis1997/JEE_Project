package com.esgi.algoBattle.resolution.domain.model;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
public class Resolution {
    private User user;
    private Algorithm algorithm;
    private Game game;
    private Long resolutionTime;
    private Boolean solved;
    private List<LinterError> linterErrors;
    private LocalDateTime startedTime;

    public Boolean isSolved() {
        return Boolean.TRUE.equals(solved);
    }
}
