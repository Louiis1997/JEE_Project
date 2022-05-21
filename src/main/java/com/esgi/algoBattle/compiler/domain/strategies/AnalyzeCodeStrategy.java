package com.esgi.algoBattle.compiler.domain.strategies;

import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.model.LinterError;

import java.util.List;
import java.util.Set;

public interface AnalyzeCodeStrategy {
    Set<LinterError> findDuplicatedLines(String sourceCode);

    List<LinterError> findTooLargeMethods(String sourceCode);

    Set<LinterError> findTooLongLines(String sourceCode);

    Language getStrategyLanguage();
}
