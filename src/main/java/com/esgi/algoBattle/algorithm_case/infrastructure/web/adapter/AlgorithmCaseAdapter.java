package com.esgi.algoBattle.algorithm_case.infrastructure.web.adapter;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.response.AlgorithmCaseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlgorithmCaseAdapter {
    public AlgorithmCaseResponse toResponse(AlgorithmCase algorithmCase) {
        return new AlgorithmCaseResponse()
                .setId(algorithmCase.getId())
                .setName(algorithmCase.getName())
                .setOutputType(algorithmCase.getOutputType())
                .setExpectedOutput(algorithmCase.getExpectedOutput())
                .setAlgorithm(algorithmCase.getAlgorithm());
    }

    public List<AlgorithmCaseResponse> toResponses(List<AlgorithmCase> algorithmCases) {
        return algorithmCases
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
