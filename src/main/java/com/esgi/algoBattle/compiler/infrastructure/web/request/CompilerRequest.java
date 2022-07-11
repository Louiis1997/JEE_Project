package com.esgi.algoBattle.compiler.infrastructure.web.request;

import com.esgi.algoBattle.compiler.domain.model.Language;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompilerRequest {
    Long algorithmId;
    String sourceCode;
    Language language;
}


