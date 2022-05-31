package com.esgi.algoBattle.case_input.use_case;

import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCaseInputById {

    private final CaseInputDAO caseInputDAO;
    private final AlgorithmCaseDAO algorithmCaseDAO;

    public CaseInput execute(Long caseId, Long id) {
        AlgorithmCase algorithmCase = checkIfCaseExists(caseId);

        var caseInput = caseInputDAO.findById(id);
        if (caseInput == null) {
            throw new NullPointerException(String.format("Input with id %s not found", id));
        }
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
