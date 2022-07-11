package com.esgi.algoBattle.case_input.infrastructure.dataprovider.repository;

import com.esgi.algoBattle.case_input.infrastructure.dataprovider.entity.CaseInputEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseInputRepository extends JpaRepository<CaseInputEntity, Long> {
    List<CaseInputEntity> findAllByAlgorithmCaseId(Long algoId);
}
