package com.esgi.algoBattle.compiler.infrastructure.utils;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.compiler.domain.exception.CompilationException;
import com.esgi.algoBattle.compiler.domain.exception.ContainerExecutionException;
import com.esgi.algoBattle.compiler.domain.exception.DockerBuildException;
import com.esgi.algoBattle.compiler.domain.model.CaseExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.infrastructure.models.CodexExecuteResponse;
import com.esgi.algoBattle.compiler.infrastructure.utils.interfaces.RunnableCase;
import com.esgi.algoBattle.compiler.infrastructure.utils.interfaces.VerifiableOutput;
import com.esgi.algoBattle.compiler.infrastructure.utils.strategies.CodexAPIService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CodeRunManager implements RunnableCase, VerifiableOutput {
    private final MainClassGenerateManager mainClassGenerateManager;
    private final CodexAPIService executorApiService;

    Logger logger = LogManager.getLogger(CodeRunManager.class);

    public List<CaseExecutionOutput> runAndVerifyCases(Algorithm algo, String sourceCode, Language language) throws CompilationException, DockerBuildException, ContainerExecutionException {
        Map<Integer, String> sourceCodeExecutables = new HashMap<>();
        IntStream.range(0, algo.getCases().size()).forEach(i -> {
            String sourceCodeExecutable = mainClassGenerateManager.execute(algo.getCases().get(i), String.valueOf(i + 1), algo.getFuncName(), sourceCode, language);
            sourceCodeExecutables.put(i, sourceCodeExecutable);
        });

        var caseExecutionOutputs = new LinkedList<CaseExecutionOutput>();

        for (int i = 0; i < algo.getCases().size(); i++) {
            var errorOutput = "";
            var successOutput = "";
            var isTestSuccessful = false;

            String currentCode = sourceCodeExecutables.get(i);
            logger.info("Executing code..");
            CodexExecuteResponse result = executorApiService.post(currentCode, language.toString());
            logger.info("End of execution");
            logger.info("Result from codex :\n" + result);

            int status = result.error == null ? 0 : 1;
            errorOutput = result.error == null ? "" : result.error;

            if (!errorOutput.isEmpty())
                throw new CompilationException(errorOutput);

            successOutput = result.output;
            isTestSuccessful = verifyOutput(algo.getCases().get(i), successOutput);

            CaseExecutionOutput caseExecutionOutput = new CaseExecutionOutput(status, algo.getCases().get(i).getName(), successOutput, algo.getCases().get(i).getExpectedOutput(), isTestSuccessful);
            caseExecutionOutputs.add(caseExecutionOutput);
        }

        return caseExecutionOutputs;
    }


    public boolean verifyOutput(AlgorithmCase runCase, String output) {
        output = output.replace("\r", "");
        output = output.replace("\n", "");
        return output.equals(runCase.getExpectedOutput());
    }
}

