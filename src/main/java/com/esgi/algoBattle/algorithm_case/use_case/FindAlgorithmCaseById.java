package com.esgi.algoBattle.algorithm_case.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAlgorithmCaseById {

    private final AlgorithmCaseDAO algorithmCaseDAO;
    private final AlgorithmDAO algorithmDAO;

    public AlgorithmCase execute(Long algoId, Long id) {
        Algorithm algorithm = checkIfAlgoExists(algoId);

        var algoCase = algorithmCaseDAO.findById(id);
        if (algoCase == null) {
            throw new NullPointerException(String.format("AlgoCase with id %s not found", id));
        }
        algoCase.setAlgorithm(algorithm);
        return algoCase;
    }

    private Algorithm checkIfAlgoExists(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            System.out.printf("Algo with id %s not found", id);
        }
        return exist;
    }
}
