package com.esgi.algoBattle.case_input.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class CaseInputRequest {
    @NotBlank
    private String value;
}
