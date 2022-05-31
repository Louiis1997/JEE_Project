package com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name = "algorithm_case")
@Accessors(chain = true)
@Data
public class AlgorithmCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "output_type")
    private String outputType;

    @Column(columnDefinition = "TEXT", name = "expected_output")
    private String expectedOutput;

    @Column(name = "algo_id")
    private Long algoId;
}
