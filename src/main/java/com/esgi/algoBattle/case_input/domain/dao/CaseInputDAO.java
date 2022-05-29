package com.esgi.algoBattle.case_input.domain.dao;

import com.esgi.algoBattle.case_input.domain.model.CaseInput;

import java.util.List;

public interface CaseInputDAO {
    CaseInput create(CaseInput caseInput);

    CaseInput findById(Long id);

    List<CaseInput> findAllByCase(Long caseId);

    CaseInput update(CaseInput caseInput);

    void delete(Long id);
}

