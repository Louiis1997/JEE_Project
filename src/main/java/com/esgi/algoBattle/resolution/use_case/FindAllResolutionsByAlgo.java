package com.esgi.algoBattle.resolution.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.resolution.domain.dao.ResolutionDAO;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllResolutionsByAlgo {

    private final ResolutionDAO resolutionDAO;
    private final AlgorithmDAO algorithmDAO;

    public List<Resolution> execute(Long algorithmId) {
        checkIfAlgorithmExists(algorithmId);
        return resolutionDAO.findResolutionsByAlgo(algorithmId);
    }

    private void checkIfAlgorithmExists(Long id) {
        Algorithm exist = algorithmDAO.findById(id);
        if (exist == null) {
            throw new NotFoundException(String.format("Algorithm with id %s not found", id));
        }
    }

}
