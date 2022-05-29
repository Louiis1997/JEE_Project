package com.esgi.algoBattle.algorithm_case.domain.dao;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;

import java.util.List;

public interface AlgorithmCaseDAO {
    AlgorithmCase create(AlgorithmCase algorithmCase);

    AlgorithmCase findById(Long id);

    AlgorithmCase findByName(Long algoId, String name);

    List<AlgorithmCase> findAllByAlgo(Long algoId);

    AlgorithmCase update(AlgorithmCase algorithmCase);

    void delete(Long id);
}
