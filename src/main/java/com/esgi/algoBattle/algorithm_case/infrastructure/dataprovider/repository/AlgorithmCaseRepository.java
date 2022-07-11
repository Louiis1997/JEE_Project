package com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.repository;


import com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.entity.AlgorithmCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmCaseRepository extends JpaRepository<AlgorithmCaseEntity, Long> {
    AlgorithmCaseEntity findByAlgoIdAndName(Long algoId, String name);

    List<AlgorithmCaseEntity> findAllByAlgoId(Long algoId);
}
