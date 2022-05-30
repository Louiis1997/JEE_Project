package com.esgi.algoBattle.algorithm_case.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllAlgorithmCasesByAlgo {

    private final AlgorithmCaseDAO algorithmCaseDAO;
    private final AlgorithmDAO algorithmDAO;

    public List<AlgorithmCase> execute(Long algoId) {
        checkIfAlgoExists(algoId);

        return algorithmCaseDAO.findAllByAlgo(algoId);
    }

    private void checkIfAlgoExists(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            System.out.printf("Algo with id %s not found", id);
        }
    }
}
