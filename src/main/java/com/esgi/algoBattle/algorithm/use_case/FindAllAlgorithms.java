package com.esgi.algoBattle.algorithm.use_case;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllAlgorithms {

    private final AlgorithmDAO algorithmDAO;

    public List<Algorithm> execute() {
        return algorithmDAO.findAll();
    }
}
