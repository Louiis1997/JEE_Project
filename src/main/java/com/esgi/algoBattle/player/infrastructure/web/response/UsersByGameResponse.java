package com.esgi.algoBattle.player.infrastructure.web.response;

import com.esgi.algoBattle.user.domain.model.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class UsersByGameResponse implements Serializable {
    private User user;
    private Integer remainingHealthPoints;
    private Boolean won;
}
