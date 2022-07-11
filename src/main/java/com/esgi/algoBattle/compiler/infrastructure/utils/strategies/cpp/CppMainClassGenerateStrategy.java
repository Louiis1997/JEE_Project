package com.esgi.algoBattle.compiler.infrastructure.utils.strategies.cpp;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.compiler.domain.strategies.MainClassGenerateStrategy;
import com.esgi.algoBattle.compiler.infrastructure.utils.constants.MainClasses;

public class CppMainClassGenerateStrategy implements MainClassGenerateStrategy {

    @Override
    public String generateMainClass(AlgorithmCase runCase, String mainFileIndex, String funcName, String sourceCode) {
        return String.format(MainClasses.CPP, sourceCode, generateCasesFunctions(runCase, funcName));
    }

    private String generateCasesFunctions(AlgorithmCase runCase, String funcName) {
        String result = "";

        runCase.setOutputType(runCase.getOutputType().toLowerCase());

        StringBuilder line = new StringBuilder(runCase.getOutputType() + " runCase = " + funcName + "(");
        for (int i = 0; i < runCase.getInput().size(); i++) {
            if (i == 0) {
                line.append(runCase.getInput().get(i).getValue());
            } else {
                line.append(",").append(runCase.getInput().get(i).getValue());
            }
        }
        line.append(");\n\t");
        line.append("cout << runCase;\n\t");
        result += line.toString();
        return result;
    }
}
