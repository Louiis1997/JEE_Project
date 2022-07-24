package com.esgi.algoBattle.compiler.infrastructure.web.controller;

import com.esgi.algoBattle.compiler.domain.model.ExecutionOutput;
import com.esgi.algoBattle.compiler.infrastructure.web.request.CompilerRequest;
import com.esgi.algoBattle.compiler.use_case.AnalyzeCode;
import com.esgi.algoBattle.compiler.use_case.CompileCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/compiler")
@RequiredArgsConstructor
public class CompilerController {

    private final CompileCode compileCode;
    private final AnalyzeCode analyzeCode;

    @PostMapping
    public ResponseEntity<ExecutionOutput> compile(@Valid @RequestBody CompilerRequest request) {
        try {
            final var result = compileCode.execute(request.getAlgorithmId(), request.getSourceCode(), request.getLanguage());
            final var linterErrors = analyzeCode.execute(request.getSourceCode(), request.getLanguage());
            result.setLinterErrors(linterErrors);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
