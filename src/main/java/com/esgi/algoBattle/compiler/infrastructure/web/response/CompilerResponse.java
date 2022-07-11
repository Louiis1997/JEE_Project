package com.esgi.algoBattle.compiler.infrastructure.web.response;

import com.esgi.algoBattle.compiler.domain.model.Status;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class CompilerResponse implements Serializable {
    private String output;

    private Status status;

    private boolean successfullyCompiled;

    private LocalDateTime date;
}
