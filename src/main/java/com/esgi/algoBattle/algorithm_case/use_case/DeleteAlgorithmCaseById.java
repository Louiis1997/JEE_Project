package com.esgi.algoBattle.algorithm_case.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAlgorithmCaseById {

    private final AlgorithmCaseDAO algorithmCaseDAO;
    private final AlgorithmDAO algorithmDAO;

    public void execute(Long algoId, Long id) {
        checkIfAlgoExists(algoId);
        checkIfExists(id);
        algorithmCaseDAO.delete(id);
    }

    private void checkIfExists(Long id) {
        var algoCase = algorithmCaseDAO.findById(id);
        if (algoCase == null) {
            System.out.printf("AlgoCase with id %s not found", id);
        }
    }

    private void checkIfAlgoExists(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            System.out.printf("Algo with id %s not found", id);
        }
    }
}
