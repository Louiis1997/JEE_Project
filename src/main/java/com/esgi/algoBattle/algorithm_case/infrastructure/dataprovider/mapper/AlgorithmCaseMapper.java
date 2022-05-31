package com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.entity.AlgorithmCaseEntity;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmCaseMapper {
    public AlgorithmCase toDomain(AlgorithmCaseEntity entity) {
        return new AlgorithmCase()
                .setId(entity.getId())
                .setName(entity.getName())
                .setOutputType(entity.getOutputType())
                .setExpectedOutput(entity.getExpectedOutput());
    }

    public AlgorithmCaseEntity toEntity(AlgorithmCase algorithmCase) {
        return new AlgorithmCaseEntity()
                .setName(algorithmCase.getName())
                .setOutputType(algorithmCase.getOutputType())
                .setExpectedOutput(algorithmCase.getExpectedOutput())
                .setAlgoId(algorithmCase.getAlgorithm().getId());
    }
}
