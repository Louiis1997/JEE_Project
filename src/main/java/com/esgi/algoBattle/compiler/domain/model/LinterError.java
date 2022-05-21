package com.esgi.algoBattle.compiler.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LinterError {
    private Integer errorNumber;
    private String errorMessage;
}
