package com.esgi.algoBattle.algorithm.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAlgorithmById {

    private final AlgorithmDAO algorithmDAO;

    public void execute(Long id) {
        var algorithm = algorithmDAO.findById(id);
        if (algorithm == null) {
            System.out.printf("Algorithm with id %s not found%n", id);
        }
        algorithmDAO.delete(id);
    }
}
