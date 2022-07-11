package com.esgi.algoBattle.case_input.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.dataprovider.entity.CaseInputEntity;
import org.springframework.stereotype.Component;

@Component
public class CaseInputMapper {
    public CaseInput toDomain(CaseInputEntity entity) {
        return new CaseInput()
                .setId(entity.getId())
                .setValue(entity.getValue());
    }

    public CaseInputEntity toEntity(CaseInput caseInput) {
        return new CaseInputEntity()
                .setValue(caseInput.getValue())
                .setAlgorithmCaseId(caseInput.getAlgorithmCase().getId());
    }
}
