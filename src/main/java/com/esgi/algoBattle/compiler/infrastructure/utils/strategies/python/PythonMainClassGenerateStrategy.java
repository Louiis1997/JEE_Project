package com.esgi.algoBattle.compiler.infrastructure.utils.strategies.python;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.compiler.domain.strategies.MainClassGenerateStrategy;

public class PythonMainClassGenerateStrategy implements MainClassGenerateStrategy {

    @Override
    public String generateMainClass(AlgorithmCase runCase, String mainFileIndex, String funcName, String sourceCode) {

        return sourceCode + generateCasesFunctions(runCase, funcName);
    }

    private String generateCasesFunctions(AlgorithmCase runCase, String funcName) {
        String result = "\n";

        StringBuilder line = new StringBuilder("runCase = " + funcName + "(");
        for (int i = 0; i < runCase.getInput().size(); i++) {
            if (i == 0) {
                line.append(runCase.getInput().get(i).getValue());
            } else {
                line.append(",").append(runCase.getInput().get(i).getValue());
            }
        }
        line.append(")\n");
        line.append("print(runCase)\n");
        result += line.toString();
        return result;
    }
}
