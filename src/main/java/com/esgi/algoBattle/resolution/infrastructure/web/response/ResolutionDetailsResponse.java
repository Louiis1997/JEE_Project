package com.esgi.algoBattle.resolution.infrastructure.web.response;

import com.esgi.algoBattle.player.domain.model.Player;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class ResolutionDetailsResponse implements Serializable {
    private String algorithmName;
    private List<Player> players;
    private Long errorNumber;
    private Long resolutionTime;
    private Boolean solved;
}
