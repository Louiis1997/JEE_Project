package com.esgi.algoBattle.case_input.infrastructure.web.response;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class CaseInputResponse implements Serializable {
    private Long id;
    private String value;
    private AlgorithmCase algorithmCase;
}
