package com.esgi.algoBattle.algorithm.infrastructure.web.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class AlgorithmResponse implements Serializable {
    private Long id;
    private String wording;
    private String funcName;
    private String description;
    private String shortDescription;
    private String javaInitialCode;
    private String pythonInitialCode;
    private String cppInitialCode;
    private Integer timeToSolve;
    private Integer timeLimit;
    private Integer complexity;
    private Integer memoryLimit;
}
