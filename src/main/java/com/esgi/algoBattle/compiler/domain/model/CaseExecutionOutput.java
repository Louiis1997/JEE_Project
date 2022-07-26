package com.esgi.algoBattle.compiler.domain.model;

import lombok.Data;

@Data
public class CaseExecutionOutput {
    private Status status;

    private String name;

    private String log;

    private String expectedOutput;

    private boolean isSuccessful;

    public CaseExecutionOutput(int status, String name, String log, String expectedOutput, boolean isSuccessful) {
        this.status = statusResponse(status);
        this.name = name;
        this.log = log;
        this.expectedOutput = expectedOutput;
        this.isSuccessful = isSuccessful;
    }

    public CaseExecutionOutput() {
    }

    private Status statusResponse(int status) {
        return switch (status) {
            case 0 -> Status.Success;
            case 1 -> Status.RuntimeError;
            case 2 -> Status.CompilationError;
            case 139 -> Status.OutOfMemory;
            default -> Status.TimeLimitExcess;
        };
    }
}
