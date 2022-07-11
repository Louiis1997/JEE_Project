package com.esgi.algoBattle.player.infrastructure.dataprovider.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity(name = "player")
@Accessors(chain = true)
@Data
@IdClass(PlayerId.class)
public class PlayerEntity implements Serializable {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "remaining_health_points")
    private Integer remainingHealthPoints;

    @Column
    private Boolean won;
}
