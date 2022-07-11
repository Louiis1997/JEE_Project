package com.esgi.algoBattle.algorithm.domain.model;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class Algorithm {
    private Long id;
    private String wording;
    private String funcName;
    private String description;
    private String shortDescription;
    private String javaInitialCode;
    private String pythonInitialCode;
    private String cppInitialCode;
    private List<AlgorithmCase> cases;
    private Integer timeToSolve;
    private Integer timeLimit;
    private Integer complexity;
    private Integer memoryLimit;
}
