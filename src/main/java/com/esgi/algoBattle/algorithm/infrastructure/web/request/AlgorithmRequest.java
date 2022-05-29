package com.esgi.algoBattle.algorithm.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Accessors(chain = true)
public class AlgorithmRequest {
    @NotBlank
    private String wording;

    @NotBlank
    private String funcName;

    @NotBlank
    private String description;

    @NotBlank
    private String shortDescription;

    @NotBlank
    private String javaInitialCode;

    @NotBlank
    private String pythonInitialCode;

    @NotBlank
    private String cppInitialCode;

    @NotNull
    @Positive
    private Integer timeToSolve;

    @NotNull
    @Positive
    private Integer timeLimit;

    @NotNull
    @Positive
    private Integer complexity;

    @NotNull
    @Positive
    private Integer memoryLimit;
}
