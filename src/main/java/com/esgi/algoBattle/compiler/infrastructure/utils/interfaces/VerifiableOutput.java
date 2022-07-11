package com.esgi.algoBattle.compiler.infrastructure.utils.interfaces;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;

public interface VerifiableOutput {
    boolean verifyOutput(AlgorithmCase runCase, String output);
}
