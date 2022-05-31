package com.esgi.algoBattle.case_input.use_case;

import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.web.request.CaseInputRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCaseInput {

    private final CaseInputDAO caseInputDAO;
    private final AlgorithmCaseDAO algorithmCaseDAO;

    public CaseInput execute(Long caseId, CaseInputRequest request) {
        AlgorithmCase algorithmCase = checkIfCaseExists(caseId);

        CaseInput caseInput = new CaseInput()
                .setValue(request.getValue())
                .setAlgorithmCase(algorithmCase);

        caseInput = caseInputDAO.create(caseInput);
        caseInput.setAlgorithmCase(algorithmCase);
        return caseInput;
    }

    private AlgorithmCase checkIfCaseExists(Long id) {
        AlgorithmCase exist = algorithmCaseDAO.findById(id);
        if (exist == null) {
            System.out.printf("Case with id %s not found", id);
        }
        return exist;
    }
}
