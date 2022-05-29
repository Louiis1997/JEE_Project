package com.esgi.algoBattle.algorithm.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.algorithm.domain.dao.AlgorithmDAO;
import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.entity.AlgorithmEntity;
import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.mapper.AlgorithmMapper;
import com.esgi.algoBattle.algorithm.infrastructure.dataprovider.repository.AlgorithmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmMySqlDao implements AlgorithmDAO {

    private final AlgorithmRepository repository;
    private final AlgorithmMapper mapper;

    @Override
    public Algorithm create(Algorithm algorithm) {
        AlgorithmEntity entity = mapper.toEntity(algorithm);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Algorithm findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Algorithm> findByPower(Integer power, Long userId) {
        return null;
    }

    @Override
    public Algorithm findByWording(String wording) {
        return Optional.ofNullable(repository.findByWording(wording))
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Algorithm> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Algorithm update(Algorithm algorithm) {
        AlgorithmEntity algorithmEntity = repository.getOne(algorithm.getId());
        algorithmEntity.setWording(algorithm.getWording());
        algorithmEntity.setFuncName(algorithm.getFuncName());
        algorithmEntity.setTimeToSolve(algorithm.getTimeToSolve());
        algorithmEntity.setTimeLimit(algorithm.getTimeLimit());
        algorithmEntity.setComplexity(algorithm.getComplexity());
        algorithmEntity.setMemoryLimit(algorithm.getMemoryLimit());
        return mapper.toDomain(repository.save(algorithmEntity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Algorithm> findAllByIds(List<Long> ids) {
        return repository.findAllById(ids)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
