package com.esgi.algoBattle.algorithm.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.mapper.AlgorithmMapper;
import com.esgi.algoBattle.algorithm.infrastructure.web.request.AlgorithmRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@AutoConfigureTestDatabase
class CreateAlgorithmTest {

    private Algorithm algorithm1;
    private AlgorithmRequest goodRequest;
    private AlgorithmRequest badRequest;


    @Autowired
    private CreateAlgorithm createAlgorithm;

    @Autowired
    private AlgorithmMapper mapper;

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

        goodRequest = new AlgorithmRequest()
                .setComplexity(1)
                .setShortDescription("Soustraction de nombres")
                .setDescription("Soustraction de nombres")
                .setCppInitialCode("int substract(int *numbers) {}")
                .setJavaInitialCode("public int substract(List<int> numbers) {}")
                .setPythonInitialCode("def substract(numbers):")
                .setFuncName("substract")
                .setTimeLimit(500)
                .setTimeToSolve(34)
                .setMemoryLimit(400)
                .setWording("Complétez la fonction substract afin de renvoyer la soustraction des nombres passés en paramètre");

        badRequest = new AlgorithmRequest()
                .setComplexity(1)
                .setShortDescription(null)
                .setDescription("Soustraction de nombres")
                .setCppInitialCode("int substract(int *numbers) {}")
                .setJavaInitialCode("public int substract(List<int> numbers) {}")
                .setPythonInitialCode("def substract(numbers):")
                .setFuncName("substract")
                .setTimeLimit(500)
                .setTimeToSolve(null)
                .setMemoryLimit(400)
                .setWording("Complétez la fonction substract afin de renvoyer la soustraction des nombres passés en paramètre");

    }

    @Test
    void when_algorithm_request_is_valid_then_return_created_algorithm() {

        Algorithm algorithm = new Algorithm()
                .setWording(goodRequest.getWording())
                .setFuncName(goodRequest.getFuncName())
                .setPythonInitialCode(goodRequest.getPythonInitialCode())
                .setJavaInitialCode(goodRequest.getJavaInitialCode())
                .setCppInitialCode(goodRequest.getCppInitialCode())
                .setDescription(goodRequest.getDescription())
                .setShortDescription(goodRequest.getShortDescription())
                .setTimeToSolve(goodRequest.getTimeToSolve())
                .setTimeLimit(goodRequest.getTimeLimit())
                .setComplexity(goodRequest.getComplexity())
                .setMemoryLimit(goodRequest.getMemoryLimit());

        Mockito.when(algorithmDAO.create(algorithm)).thenReturn(algorithm1);
        Algorithm algorithmResponse = createAlgorithm.execute(goodRequest);
        Assertions.assertEquals(algorithm1, algorithmResponse);
    }

    @Test
    void when_algorithm_request_is_not_valid_then_throw_null_pointer_exception() {

        Algorithm algorithm = new Algorithm()
                .setWording(badRequest.getWording())
                .setFuncName(badRequest.getFuncName())
                .setPythonInitialCode(badRequest.getPythonInitialCode())
                .setJavaInitialCode(badRequest.getJavaInitialCode())
                .setCppInitialCode(badRequest.getCppInitialCode())
                .setDescription(badRequest.getDescription())
                .setShortDescription(badRequest.getShortDescription())
                .setTimeToSolve(badRequest.getTimeToSolve())
                .setTimeLimit(badRequest.getTimeLimit())
                .setComplexity(badRequest.getComplexity())
                .setMemoryLimit(badRequest.getMemoryLimit());
        Mockito.when(algorithmDAO.create(algorithm)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> createAlgorithm.execute(badRequest));
    }

    @Test
    void when_algorithm_request_is_null_then_throw_null_pointer_exception() {
        Assertions.assertThrows(NullPointerException.class, () -> createAlgorithm.execute(null));
    }
}