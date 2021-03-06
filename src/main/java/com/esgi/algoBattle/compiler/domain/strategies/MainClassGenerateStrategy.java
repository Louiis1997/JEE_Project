package com.esgi.algoBattle.compiler.domain.strategies;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;

public interface MainClassGenerateStrategy {
    String generateMainClass(AlgorithmCase runCase, String mainFileIndex, String funcName, String sourceCode);
}
