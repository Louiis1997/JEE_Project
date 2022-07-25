package com.esgi.algoBattle.case_input.use_case;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.web.request.CaseInputRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CreateCaseInputTest {
    private CaseInput caseInput1;
    private Algorithm algorithm1;
    private AlgorithmCase algorithmCase1;
    private CaseInputRequest goodRequest;
    private CaseInputRequest badRequest;

    @Autowired
    private CreateCaseInput createCaseInput;

    @MockBean
    private CaseInputDAO caseInputDAO;

    @MockBean
    private AlgorithmCaseDAO algorithmCaseDAO;

    @BeforeEach
    public void initEach() {
        algorithm1 = new Algorithm()
                .setId(1L)
                .setWording("Complétez la fonction sum afin de renvoyer la somme des nombres passés en paramètre")
                .setFuncName("sum")
                .setPythonInitialCode("def sum(numbers):")
                .setJavaInitialCode("public int sum(List<int> numbers) {}")
                .setCppInitialCode("int sum(int *numbers) {}")
                .setDescription("Addition de nombres")
                .setShortDescription("Addition de nombres")
                .setTimeToSolve(180)
                .setTimeLimit(180)
                .setComplexity(1)
                .setMemoryLimit(400);

        goodRequest = new CaseInputRequest()
                .setValue("[5, 2, 2]");

        badRequest = new CaseInputRequest()
                .setValue(null);

        algorithmCase1 = new AlgorithmCase()
                .setId(1L)
                .setExpectedOutput("9")
                .setName("Avec nombres positifs")
                .setOutputType("int")
                .setAlgorithm(algorithm1);

        caseInput1 = new CaseInput()
                .setId(1L)
                .setValue("[5, 2, 2]")
                .setAlgorithmCase(algorithmCase1);
    }

    @Test
    void when_input_case_request_is_valid_then_return_created_input_case() {
        Long algorithmCaseId = 1L;
        Mockito.when(algorithmCaseDAO.findById(algorithmCaseId)).thenReturn(algorithmCase1);


        CaseInput caseInput = new CaseInput()
                .setValue(goodRequest.getValue())
                .setAlgorithmCase(algorithmCase1);

        Mockito.when(caseInputDAO.create(caseInput)).thenReturn(caseInput1);
        CaseInput caseInputResponse = createCaseInput.execute(algorithmCaseId,goodRequest);
        Assertions.assertEquals(caseInput1, caseInputResponse);
    }

    @Test
    void when_input_case_request_is_not_valid_then_throw_null_pointer_exception() {
        Long algorithmCaseId = 1L;
        Mockito.when(algorithmCaseDAO.findById(algorithmCaseId)).thenReturn(algorithmCase1);
        Assertions.assertThrows( NullPointerException.class, () -> createCaseInput.execute(algorithmCaseId, badRequest));
    }

    @Test
    void when_input_case_request_is_null_then_throw_null_pointer_exception() {
        Long algorithmCaseId = 1L;
        Mockito.when(algorithmCaseDAO.findById(algorithmCaseId)).thenReturn(algorithmCase1);
        Assertions.assertThrows( NullPointerException.class, () -> createCaseInput.execute(algorithmCaseId,null));
    }
}