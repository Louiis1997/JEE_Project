package com.esgi.algoBattle.compiler.infrastructure.utils;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.strategies.MainClassGenerateStrategy;
import com.esgi.algoBattle.compiler.infrastructure.utils.strategies.cpp.CppMainClassGenerateStrategy;
import com.esgi.algoBattle.compiler.infrastructure.utils.strategies.java.JavaMainClassGenerateStrategy;
import com.esgi.algoBattle.compiler.infrastructure.utils.strategies.python.PythonMainClassGenerateStrategy;
import org.springframework.stereotype.Service;

@Service
public class MainClassGenerateManager {
    private MainClassGenerateStrategy strategy = new JavaMainClassGenerateStrategy();

    public String execute(AlgorithmCase runCase, String fileName, String funcName, String sourceCode, Language language) {
        setStrategy(language);
        return strategy.generateMainClass(runCase, fileName, funcName, sourceCode);
    }

    public MainClassGenerateStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Language language) {
        switch (language) {
            case Java -> strategy = new JavaMainClassGenerateStrategy();
            case Python -> strategy = new PythonMainClassGenerateStrategy();
            case Cpp -> strategy = new CppMainClassGenerateStrategy();
        }
    }
}
