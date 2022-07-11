package com.esgi.algoBattle.case_input.infrastructure.web.adapter;

import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.web.response.CaseInputResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CaseInputAdapter {
    public CaseInputResponse toResponse(CaseInput caseInput) {
        return new CaseInputResponse()
                .setId(caseInput.getId())
                .setValue(caseInput.getValue())
                .setAlgorithmCase(caseInput.getAlgorithmCase());
    }

    public List<CaseInputResponse> toResponses(List<CaseInput> caseInputs) {
        return caseInputs
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
