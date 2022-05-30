package com.esgi.algoBattle.algorithm_case.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.request.AlgorithmCaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAlgorithmCase {

    private final AlgorithmCaseDAO algorithmCaseDAO;
    private final AlgorithmDAO algorithmDAO;

    public AlgorithmCase execute(Long algoId, Long id, AlgorithmCaseRequest request) {
        checkIfExists(id);
        Algorithm algorithm = checkIfAlgoExists(algoId);
        checkIfNameAlreadyExists(request.getName(), algoId, id);

        AlgorithmCase algorithmCase = new AlgorithmCase()
                .setId(id)
                .setName(request.getName())
                .setOutputType(request.getOutputType())
                .setExpectedOutput(request.getExpectedOutput())
                .setAlgorithm(algorithm);

        algorithmCase = algorithmCaseDAO.update(algorithmCase);
        algorithmCase.setAlgorithm(algorithm);
        return algorithmCase;
    }

    private void checkIfExists(Long id) {
        var algoCase = algorithmCaseDAO.findById(id);
        if (algoCase == null) {
            System.out.printf("AlgoCase with id %s not found", id);
        }
    }

    private Algorithm checkIfAlgoExists(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            System.out.printf("Algo with id %s not found", id);
        }
        return exist;
    }

    private void checkIfNameAlreadyExists(String name, Long algoId, Long id) {
        AlgorithmCase exist = algorithmCaseDAO.findByName(algoId, name);
        if (exist != null && !exist.getId().equals(id)) {
            System.out.printf("AlgoCase with name %s already exists for algo with id %s", name, algoId);
        }
    }
}
