package com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.entity.AlgorithmCaseEntity;
import com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.mapper.AlgorithmCaseMapper;
import com.esgi.algoBattle.algorithm_case.infrastructure.dataprovider.repository.AlgorithmCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlgorithmCaseMySqlDao implements AlgorithmCaseDAO {

    private final AlgorithmCaseRepository repository;
    private final AlgorithmCaseMapper mapper;

    @Override
    public AlgorithmCase create(AlgorithmCase algorithmCase) {
        AlgorithmCaseEntity entity = mapper.toEntity(algorithmCase);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public AlgorithmCase findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public AlgorithmCase findByName(Long algoId, String name) {
        return Optional.ofNullable(repository.findByAlgoIdAndName(algoId, name))
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<AlgorithmCase> findAllByAlgo(Long algoId) {
        return repository.findAllByAlgoId(algoId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public AlgorithmCase update(AlgorithmCase algorithmCase) {
        AlgorithmCaseEntity algorithmCaseEntity = repository.getOne(algorithmCase.getId());
        algorithmCaseEntity.setName(algorithmCase.getName());
        algorithmCaseEntity.setOutputType(algorithmCase.getOutputType());
        algorithmCaseEntity.setExpectedOutput(algorithmCase.getExpectedOutput());
        algorithmCaseEntity.setAlgoId(algorithmCase.getAlgorithm().getId());
        return mapper.toDomain(repository.save(algorithmCaseEntity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
