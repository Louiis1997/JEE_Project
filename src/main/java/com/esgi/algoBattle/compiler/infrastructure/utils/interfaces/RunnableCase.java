package com.esgi.algoBattle.compiler.infrastructure.utils.interfaces;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.compiler.domain.exception.CompilationException;
import com.esgi.algoBattle.compiler.domain.exception.ContainerExecutionException;
import com.esgi.algoBattle.compiler.domain.exception.DockerBuildException;
import com.esgi.algoBattle.compiler.domain.model.CaseExecutionOutput;
import com.esgi.algoBattle.compiler.domain.model.Language;

import java.io.IOException;
import java.util.List;

public interface RunnableCase {
    List<CaseExecutionOutput> runAndVerifyCases(Algorithm algo, String sourceCode, Language language) throws IOException, CompilationException, DockerBuildException, ContainerExecutionException;
}
