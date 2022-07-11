package com.esgi.algoBattle.player.domain.model;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class Player {
    public static final int MAX_HEALTH_POINTS = 100;

    private Game game;
    private User user;
    private Integer remainingHealthPoints = 100;
    private Boolean won;
    private Boolean darkMode;

    public Boolean hasWon() {
        return Boolean.TRUE.equals(won);
    }

    public static int getMaxHealthPoints() {
        return MAX_HEALTH_POINTS;
    }

    public void canActivateDarkMode() {
        darkMode = true;
    }
}
