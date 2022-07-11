package com.esgi.algoBattle.algorithm.infrastructure.dataprovider.entity;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name = "algorithm")
@Accessors(chain = true)
@Data
public class AlgorithmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String wording;

    @Column
    private String funcName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @Column
    private String javaInitialCode;

    @Column
    private String pythonInitialCode;

    @Column
    private String cppInitialCode;

    @Column(name = "time_to_solve")
    private Integer timeToSolve;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column
    private Integer complexity;

    @Column(name = "memory_limit")
    private Integer memoryLimit;
}

