package com.esgi.algoBattle.algorithm.domain.dao;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;

import java.util.List;

public interface AlgorithmDAO {
    Algorithm create(Algorithm algorithm);

    Algorithm findById(Long id);

    List<Algorithm> findByPower(Integer power, Long userId);

    Algorithm findByWording(String wording);

    List<Algorithm> findAll();

    Algorithm update(Algorithm algorithm);

    void delete(Long id);

    List<Algorithm> findAllByIds(List<Long> ids);
}