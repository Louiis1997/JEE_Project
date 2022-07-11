package com.esgi.algoBattle.algorithm.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.web.request.AlgorithmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateAlgorithm {

    private final AlgorithmDAO algorithmDAO;

    public Algorithm execute(Long id, AlgorithmRequest request) {
        checkIfExists(id);
        checkIfWordingAlreadyExists(request.getWording(), id);

        Algorithm algorithm = new Algorithm()
                .setId(id)
                .setWording(request.getWording())
                .setFuncName(request.getFuncName())
                .setDescription(request.getDescription())
                .setShortDescription(request.getShortDescription())
                .setCppInitialCode(request.getCppInitialCode())
                .setJavaInitialCode(request.getJavaInitialCode())
                .setPythonInitialCode(request.getPythonInitialCode())
                .setTimeToSolve(request.getTimeToSolve())
                .setTimeLimit(request.getTimeLimit())
                .setComplexity(request.getComplexity())
                .setMemoryLimit(request.getMemoryLimit());

        return algorithmDAO.update(algorithm);
    }

    private void checkIfExists(Long id) {
        var algorithm = algorithmDAO.findById(id);
        if (algorithm == null) {
            System.out.printf("Algorithm with id %s not found%n", id);
        }
    }

    private void checkIfWordingAlreadyExists(String wording, Long id) {
        Algorithm exist = algorithmDAO.findByWording(wording);
        if (exist != null && !exist.getId().equals(id)) {
            System.out.printf("Algorithm with wording %s already exists%n", wording);
        }
    }
}
