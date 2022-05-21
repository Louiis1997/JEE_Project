package com.esgi.algoBattle.compiler.domain.dao;

import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;

import java.io.IOException;

public interface CompilerDAO {
    ExecutionOutput compile(Long algoId, String sourceCode, Language language) throws IOException, InterruptedException;
}
