package com.esgi.algoBattle.compiler.infrastructure.utils.strategies.java;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.compiler.domain.strategies.MainClassGenerateStrategy;
import com.esgi.algoBattle.compiler.infrastructure.utils.constants.MainClasses;

public class JavaMainClassGenerateStrategy implements MainClassGenerateStrategy {

    @Override
    public String generateMainClass(AlgorithmCase runCase, String mainFileIndex, String funcName, String sourceCode) {
        return String.format(MainClasses.JAVA, mainFileIndex, generateCasesFunctions(runCase, funcName), sourceCode);
    }

    private String generateCasesFunctions(AlgorithmCase runCase, String funcName) {
        String result = "";
        int nb = 1;

        StringBuilder line = new StringBuilder(runCase.getOutputType() + " case" + nb + " = " + funcName + "(");
        for (int i = 0; i < runCase.getInput().size(); i++) {
            if (i == 0) {
                line.append(runCase.getInput().get(i).getValue());
            } else {
                line.append(",").append(runCase.getInput().get(i).getValue());
            }
        }
        line.append(");\n\t\t");
        line.append("System.out.print(case").append(nb).append(");\n\t\t");
        result += line.toString();
        return result;
    }
}
