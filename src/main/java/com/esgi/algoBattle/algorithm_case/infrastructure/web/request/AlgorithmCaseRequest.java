package com.esgi.algoBattle.algorithm_case.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AlgorithmCaseRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String expectedOutput;

    @NotBlank
    private String outputType;
}
