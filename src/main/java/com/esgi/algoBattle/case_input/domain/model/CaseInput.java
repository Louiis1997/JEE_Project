package com.esgi.algoBattle.case_input.domain.model;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CaseInput {
    private Long id;
    private String value;
    private AlgorithmCase algorithmCase;
}
