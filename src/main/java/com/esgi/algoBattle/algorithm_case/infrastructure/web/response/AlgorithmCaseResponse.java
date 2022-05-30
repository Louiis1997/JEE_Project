package com.esgi.algoBattle.algorithm_case.infrastructure.web.response;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class AlgorithmCaseResponse implements Serializable {
    private Long id;
    private String name;
    private String outputType;
    private String expectedOutput;
    private Algorithm algorithm;
}