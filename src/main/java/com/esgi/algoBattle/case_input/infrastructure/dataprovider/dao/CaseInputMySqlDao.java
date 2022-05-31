package com.esgi.algoBattle.case_input.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.dataprovider.entity.CaseInputEntity;
import com.esgi.algoBattle.case_input.infrastructure.dataprovider.mapper.CaseInputMapper;
import com.esgi.algoBattle.case_input.infrastructure.dataprovider.repository.CaseInputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseInputMySqlDao implements CaseInputDAO {

    private final CaseInputRepository repository;
    private final CaseInputMapper mapper;

    @Override
    public CaseInput create(CaseInput caseInput) {
        CaseInputEntity entity = mapper.toEntity(caseInput);
        entity = repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public CaseInput findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<CaseInput> findAllByCase(Long caseId) {
        return repository.findAllByAlgorithmCaseId(caseId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public CaseInput update(CaseInput caseInput) {
        CaseInputEntity caseInputEntity = repository.getOne(caseInput.getId());
        caseInputEntity.setValue(caseInput.getValue());
        caseInputEntity.setAlgorithmCaseId(caseInput.getAlgorithmCase().getId());
        return mapper.toDomain(repository.save(caseInputEntity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
