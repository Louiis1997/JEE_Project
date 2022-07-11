package com.esgi.algoBattle.compiler.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ExecutionOutput {
    private boolean successfullyCompiled;

    private List<CaseExecutionOutput> cases;

    private String errorOutput;

    private boolean isSuccessful;

    private List<LinterError> linterErrors;
}
