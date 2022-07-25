package com.esgi.algoBattle.algorithm_case.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.request.AlgorithmCaseRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CreateAlgorithmCaseTest {

    private Algorithm algorithm1;
    private AlgorithmCase algorithmCase1;
    private AlgorithmCaseRequest goodRequest;
    private AlgorithmCaseRequest badRequest;


    @Autowired
    private CreateAlgorithmCase createAlgorithmCase;

    @MockBean
    private AlgorithmCaseDAO algorithmCaseDAO;

    @MockBean
    private AlgorithmDAO algorithmDAO;

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

        goodRequest = new AlgorithmCaseRequest()
                .setExpectedOutput("9")
                .setName("Avec nombres positifs")
                .setOutputType("int");

        badRequest = new AlgorithmCaseRequest()
                .setExpectedOutput(null)
                .setName(null)
                .setOutputType("int");

        algorithmCase1 = new AlgorithmCase()
                .setId(1L)
                .setExpectedOutput("9")
                .setName("Avec nombres positifs")
                .setOutputType("int")
                .setAlgorithm(algorithm1);
    }



    @Test
    void when_algorithm_case_request_is_valid_then_return_created_algorithm_case() {
        Long algorithmId = 1L;
        Mockito.when(algorithmDAO.findById(algorithmId)).thenReturn(algorithm1);


        AlgorithmCase algorithmCase = new AlgorithmCase()
                .setName(goodRequest.getName())
                .setOutputType(goodRequest.getOutputType())
                .setExpectedOutput(goodRequest.getExpectedOutput())
                .setAlgorithm(algorithm1);

        Mockito.when(algorithmCaseDAO.create(algorithmCase)).thenReturn(algorithmCase1);
        AlgorithmCase algorithmResponse = createAlgorithmCase.execute(algorithmId,goodRequest);
        Assertions.assertEquals(algorithmCase1, algorithmResponse);
    }

    @Test
    void when_algorithm_case_request_is_not_valid_then_throw_null_pointer_exception() {

        Long algorithmId = 1L;
        Mockito.when(algorithmDAO.findById(algorithmId)).thenReturn(algorithm1);

        Assertions.assertThrows( NullPointerException.class, () -> createAlgorithmCase.execute(algorithmId, badRequest));
    }

    @Test
    void when_algorithm_case_request_is_null_then_throw_null_pointer_exception() {
        Long algorithmId = 1L;
        Mockito.when(algorithmDAO.findById(algorithmId)).thenReturn(algorithm1);

        Assertions.assertThrows( NullPointerException.class, () -> createAlgorithmCase.execute(algorithmId,null));
    }
}