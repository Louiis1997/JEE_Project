package com.esgi.algoBattle.algorithm.infrastructure.dataprovider.repository;

import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.entity.AlgorithmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmRepository extends JpaRepository<AlgorithmEntity, Long> {
    AlgorithmEntity findByWording(String wording);

    List<AlgorithmEntity> findByComplexity(Integer complexity);
}
