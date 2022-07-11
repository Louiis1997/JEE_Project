package com.esgi.algoBattle.compiler.use_case;

import com.esgi.algoBattle.compiler.domain.dao.CompilerDAO;
import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompileCodeImpl implements CompileCode {

    private final CompilerDAO compilerDAO;

    @Override
    public ExecutionOutput execute(Long algoId, String sourceCode, Language language) throws IOException, InterruptedException {
        return compilerDAO.compile(algoId, sourceCode, language);
    }
}
