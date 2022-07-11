package com.esgi.algoBattle.compiler.use_case;

import com.esgi.algoBattle.compiler.domain.exception.CompilationException;
import com.esgi.algoBattle.compiler.domain.factory.AnalyzeCodeStrategyFactory;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.compiler.domain.strategies.AnalyzeCodeStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyzeCode {

    private final AnalyzeCodeStrategyFactory analyzeCodeStrategyFactory;

    public List<LinterError> execute(String sourceCode, Language language) {
        if (sourceCode == null || sourceCode.isBlank()) {
            throw new CompilationException("No source code");
        }
        AnalyzeCodeStrategy analyzeCodeStrategy = analyzeCodeStrategyFactory
                .getStrategy(language);

        List<LinterError> linterErrors = new ArrayList<>();

        linterErrors.addAll(analyzeCodeStrategy
                .findDuplicatedLines(sourceCode)
        );

        linterErrors.addAll(analyzeCodeStrategy
                .findTooLargeMethods(sourceCode)
        );

        linterErrors.addAll(analyzeCodeStrategy
                .findTooLongLines(sourceCode)
        );

        return linterErrors;
    }

}
