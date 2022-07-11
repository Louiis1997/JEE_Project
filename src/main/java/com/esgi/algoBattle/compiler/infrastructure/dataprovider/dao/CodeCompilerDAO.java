package com.esgi.algoBattle.compiler.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.use_case.FindAlgorithmById;
import com.esgi.algoBattle.algorithm_case.use_case.FindAllAlgorithmCasesByAlgo;
import com.esgi.algoBattle.case_input.use_case.FindAllCaseInputsByCase;
import com.esgi.algoBattle.compiler.domain.dao.CompilerDAO;
import com.esgi.algoBattle.compiler.domain.exception.CompilationException;
import com.esgi.algoBattle.compiler.domain.exception.ContainerExecutionException;
import com.esgi.algoBattle.compiler.domain.exception.DockerBuildException;
import com.esgi.algoBattle.compiler.domain.model.CaseExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.infrastructure.utils.interfaces.RunnableCase;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CodeCompilerDAO implements CompilerDAO {

    private final RunnableCase codeRunner;
    private final FindAlgorithmById findAlgorithmById;
    private final FindAllAlgorithmCasesByAlgo findAllAlgorithmCasesByAlgo;
    private final FindAllCaseInputsByCase findAllCaseInputsByCase;

    Logger logger = LogManager.getLogger(CodeCompilerDAO.class);

    @Override
    public ExecutionOutput compile(Long algoId, String sourceCode, Language language) {
        var algo = findAlgorithm(algoId);
        return compileAndGetExecutionOutput(algo, sourceCode, language);
    }

    private Algorithm findAlgorithm(Long algoId) {
        var algo = findAlgorithmById.execute(algoId);
        var cases = findAllAlgorithmCasesByAlgo.execute(algoId);
        algo.setCases(cases);
        for (var a : cases) {
            var inputs = findAllCaseInputsByCase.execute(a.getId());
            a.setInput(inputs);
        }
        return algo;
    }

    private ExecutionOutput compileAndGetExecutionOutput(Algorithm algo, String sourceCode, Language language) {
        List<CaseExecutionOutput> caseExecutionOutputs = null;
        var compilationError = false;
        var compilationErrorStack = "";

        try {
            caseExecutionOutputs = codeRunner.runAndVerifyCases(algo, sourceCode, language);
        } catch (DockerBuildException | ContainerExecutionException | IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (CompilationException e) {
            logger.error(e.getMessage());
            compilationError = true;
            compilationErrorStack = e.getMessage();
        }
        var isOverallSuccessful = !compilationError && Objects.requireNonNull(caseExecutionOutputs).stream().allMatch(CaseExecutionOutput::isSuccessful);

        return new ExecutionOutput()
                .setSuccessfullyCompiled(!compilationError)
                .setCases(caseExecutionOutputs)
                .setErrorOutput(compilationErrorStack)
                .setSuccessful(isOverallSuccessful);
    }

}
