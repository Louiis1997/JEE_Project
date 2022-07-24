package com.esgi.algoBattle.resolution.infrastructure.dataprovider.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "resolution")
@Accessors(chain = true)
@Data
@IdClass(ResolutionEntityId.class)
public class ResolutionEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "algo_id")
    private Long algoId;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "resolution_time")
    private Long resolutionTime;

    @Column(name = "started_time")
    private LocalDateTime startedTime;

    @Column
    private Boolean solved;

    @Column(name = "linter_errors")
    private Integer linterErrors;
}
