package com.esgi.algoBattle.algorithm.infrastructure.web.adapter;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.web.response.AlgorithmResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlgorithmAdapter {
    public AlgorithmResponse toResponse(Algorithm algorithm) {
        return new AlgorithmResponse()
                .setId(algorithm.getId())
                .setWording(algorithm.getWording())
                .setFuncName(algorithm.getFuncName())
                .setDescription(algorithm.getDescription())
                .setShortDescription(algorithm.getShortDescription())
                .setPythonInitialCode(algorithm.getPythonInitialCode())
                .setJavaInitialCode(algorithm.getJavaInitialCode())
                .setCppInitialCode(algorithm.getCppInitialCode())
                .setTimeToSolve(algorithm.getTimeToSolve())
                .setTimeLimit(algorithm.getTimeLimit())
                .setComplexity(algorithm.getComplexity())
                .setMemoryLimit(algorithm.getMemoryLimit());
    }

    public List<AlgorithmResponse> toResponses(List<Algorithm> algorithms) {
        return algorithms
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
