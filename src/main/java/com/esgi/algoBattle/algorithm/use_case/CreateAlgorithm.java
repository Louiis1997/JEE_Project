package com.esgi.algoBattle.algorithm.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.web.request.AlgorithmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAlgorithm {

    private final AlgorithmDAO algorithmDAO;

    public Algorithm execute(AlgorithmRequest request) {
        checkIfWordingAlreadyExists(request.getWording());

        Algorithm algorithm = new Algorithm()
                .setWording(request.getWording())
                .setFuncName(request.getFuncName())
                .setPythonInitialCode(request.getPythonInitialCode())
                .setJavaInitialCode(request.getJavaInitialCode())
                .setCppInitialCode(request.getCppInitialCode())
                .setDescription(request.getDescription())
                .setShortDescription(request.getShortDescription())
                .setTimeToSolve(request.getTimeToSolve())
                .setTimeLimit(request.getTimeLimit())
                .setComplexity(request.getComplexity())
                .setMemoryLimit(request.getMemoryLimit());

        return algorithmDAO.create(algorithm);
    }

    private void checkIfWordingAlreadyExists(String wording) {
        Algorithm exist = algorithmDAO.findByWording(wording);
        if (exist != null) {
            System.out.printf("Algorithm with wording %s already exists%n", wording);
        }
    }
}
