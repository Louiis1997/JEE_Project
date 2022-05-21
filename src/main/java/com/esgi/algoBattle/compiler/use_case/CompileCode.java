package com.esgi.algoBattle.compiler.use_case;

import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;

import java.io.IOException;

public interface CompileCode {
    ExecutionOutput execute(Long algoId, String sourceCode, Language language) throws IOException, InterruptedException;
}
