package com.esgi.algoBattle.compiler.use_case;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.use_case.FindAlgorithmById;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.use_case.FindAllAlgorithmCasesByAlgo;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.use_case.FindAllCaseInputsByCase;
import com.esgi.algoBattle.compiler.domain.model.CaseExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.infrastructure.models.CodexExecuteResponse;
import com.esgi.algoBattle.compiler.infrastructure.utils.strategies.CodexAPIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CompileCodeTest {
    private final Algorithm algorithm = new Algorithm();
    private final AlgorithmCase firstAlgorithmCase = new AlgorithmCase();
    private final CaseInput firstCaseInput = new CaseInput();

    @Autowired
    private CompileCode compileCode;

    @MockBean
    private FindAlgorithmById findAlgorithmById;

    @MockBean
    private FindAllAlgorithmCasesByAlgo findAllAlgorithmCasesByAlgo;

    @MockBean
    private FindAllCaseInputsByCase findAllCaseInputsByCase;

    @MockBean
    private CodexAPIService codexAPIService;

    @BeforeEach
    public void setUp() {
        firstCaseInput.setId(1L);
        firstCaseInput.setValue("[5, 2, 2]");

        firstAlgorithmCase.setId(1L);
        firstAlgorithmCase.setAlgorithm(algorithm);
        firstAlgorithmCase.setName("Avec nombres positifs");
        firstAlgorithmCase.setOutputType("int");
        firstAlgorithmCase.setExpectedOutput("9");
        firstAlgorithmCase.setInput(new ArrayList<>() {
            {
                add(firstCaseInput);
            }
        });

        firstCaseInput.setAlgorithmCase(firstAlgorithmCase);

        algorithm.setId(1L);
        algorithm.setWording("Complétez la fonction sum afin de renvoyer la somme des nombres passés en paramètre");
        algorithm.setFuncName("sum");
        algorithm.setDescription("Addition de nombres");
        algorithm.setShortDescription(null);
        algorithm.setJavaInitialCode("public int sum(List<int> numbers) {}");
        algorithm.setPythonInitialCode("def sum(numbers):");
        algorithm.setCppInitialCode("int sum(int *numbers) {}");
        algorithm.setCases(new ArrayList<>() {
            {
                add(firstAlgorithmCase);
            }
        });
        algorithm.setTimeToSolve(180);
        algorithm.setTimeLimit(180);
        algorithm.setComplexity(1);
        algorithm.setMemoryLimit(400);
    }

    // Compile code with (Long algoId, String sourceCode, Language language)
    @Test
    public void when_compiling_correct_code() throws IOException, InterruptedException {
        var compilerServiceResponse = new CodexExecuteResponse();
        compilerServiceResponse.success = (true);
        compilerServiceResponse.timestamp = new Date().toString();
        compilerServiceResponse.output = ("9");

        var sourceCode = "def sum(array):\n  sum = 0;\n  for x in array:\n    sum = sum + x;\n  return sum;";
        var language = Language.Python;

        Mockito.when(findAlgorithmById.execute(1L)).thenReturn(algorithm);
        Mockito.when(findAllAlgorithmCasesByAlgo.execute(1L)).thenReturn(algorithm.getCases());
        Mockito.when(findAllCaseInputsByCase.execute(1L)).thenReturn(firstAlgorithmCase.getInput());
        Mockito.when(codexAPIService.post(sourceCode, language.toString())).thenReturn(compilerServiceResponse);

        var output = compileCode.execute(algorithm.getId(), sourceCode, language);

        var expectedOutput = new ExecutionOutput();
        expectedOutput.setSuccessful(true);
        expectedOutput.setErrorOutput("");
        expectedOutput.setCases(new ArrayList<>() {
            {
                add(new CaseExecutionOutput(0, firstAlgorithmCase.getName(), "9\n", "9", true));
            }
        });
        expectedOutput.setSuccessfullyCompiled(true);
        expectedOutput.setLinterErrors(null);

        assertEquals(expectedOutput, output);
        assertTrue(true);
    }
}
