package com.esgi.algoBattle.case_input.use_case;

import com.esgi.algoBattle.algorithm_case.domain.dao.AlgorithmCaseDAO;
import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.case_input.domain.dao.CaseInputDAO;
import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllCaseInputsByCase {

    private final CaseInputDAO caseInputDAO;
    private final AlgorithmCaseDAO algorithmCaseDAO;

    public List<CaseInput> execute(Long caseId) {
        checkIfCaseExists(caseId);
        return caseInputDAO.findAllByCase(caseId);
    }

    private void checkIfCaseExists(Long id) {
        AlgorithmCase exist = algorithmCaseDAO.findById(id);
        if (exist == null) {
            System.out.printf("Case with id %s not found", id);
        }
    }
}
