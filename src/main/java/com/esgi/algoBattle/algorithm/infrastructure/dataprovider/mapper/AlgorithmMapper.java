package com.esgi.algoBattle.algorithm.infrastructure.dataprovider.mapper;


import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.entity.AlgorithmEntity;
import org.springframework.stereotype.Component;

@Component
public class AlgorithmMapper {
    public Algorithm toDomain(AlgorithmEntity entity) {
        return new Algorithm()
                .setId(entity.getId())
                .setWording(entity.getWording())
                .setFuncName(entity.getFuncName())
                .setDescription(entity.getDescription())
                .setShortDescription(entity.getShortDescription())
                .setPythonInitialCode(entity.getPythonInitialCode())
                .setJavaInitialCode(entity.getJavaInitialCode())
                .setCppInitialCode(entity.getCppInitialCode())
                .setTimeToSolve(entity.getTimeToSolve())
                .setTimeLimit(entity.getTimeLimit())
                .setComplexity(entity.getComplexity())
                .setMemoryLimit(entity.getMemoryLimit());
    }

    public AlgorithmEntity toEntity(Algorithm algorithm) {
        return new AlgorithmEntity()
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
}
