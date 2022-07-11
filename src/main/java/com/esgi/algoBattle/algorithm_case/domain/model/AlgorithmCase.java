package com.esgi.algoBattle.algorithm_case.domain.model;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Accessors(chain = true)
@Data
public class AlgorithmCase {
    private Long id;
    private String name;
    private List<CaseInput> input;
    private String outputType;
    private String expectedOutput;
    private Algorithm algorithm;
}