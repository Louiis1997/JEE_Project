package com.esgi.algoBattle.case_input.use_case;

import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCaseInputById {

    private final CaseInputDAO caseInputDAO;
    private final AlgorithmCaseDAO algorithmCaseDAO;

    public void execute(Long algoId, Long id) {
        checkIfCaseExists(algoId);
        checkIfExists(id);
        caseInputDAO.delete(id);
    }

    private void checkIfExists(Long id) {
        var caseInput = caseInputDAO.findById(id);
        if (caseInput == null) {
            System.out.printf("Input with id %s not found", id);
        }
    }

    private void checkIfCaseExists(Long id) {
        AlgorithmCase exist = algorithmCaseDAO.findById(id);
        if (exist == null) {
            System.out.printf("Case with id %s not found", id);
        }
    }
}
