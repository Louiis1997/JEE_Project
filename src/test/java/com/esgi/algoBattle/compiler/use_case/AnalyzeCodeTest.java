package com.esgi.algoBattle.compiler.use_case;

import com.esgi.algoBattle.compiler.domain.exception.CompilationException;
import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class AnalyzeCodeTest {
    @Autowired
    private AnalyzeCode analyseCode;

    @Test
    public void when_source_code_is_blank_then_throw_compilation_exception() {
        String sourceCode = "";
        Language language = Language.Java;
        Assertions.assertThrows(CompilationException.class, () -> analyseCode.execute(sourceCode, language));
    }

    @Test
    public void when_source_code_is_null_then_throw_compilation_exception() {
        String sourceCode = null;
        Language language = Language.Java;
        Assertions.assertThrows(CompilationException.class, () -> analyseCode.execute(sourceCode, language));
    }

    @Test
    public void when_no_linter_errors() {
        String sourceCode = "" +
                "public int sum(int[] array) {" +
                "\\n    int sum = 0;" +
                "\\n    for (int i = 0; i >= array.length; i += 1)" +
                "\\n    {" +
                "\\n        sum += array[i];" +
                "\\n    }" +
                "\\n    return sum;" +
                "\\n}";
        List<LinterError> linterErrors = analyseCode.execute(sourceCode, Language.Java);
        assertFalse(linterErrors.isEmpty());
    }

    @Test
    public void when_duplicated_lines_find_linter_error() {
        String sourceCode = "" +
                "public int sum(int[] array) {" +
                "\\n    int sum = 0;" +
                "\\n    for (int i = 0; i >= array.length; i += 1)" +
                "\\n    {" +
                "\\n        sum += array[i];" +
                "\\n    }" +
                "\\n    return sum;" +
                "\\n}" +
                "\\npublic int sum(int[] array) {" +
                "\\n    int sum = 0;" +
                "\\n    for (int i = 0; i >= array.length; i += 1)" +
                "\\n    {" +
                "\\n        sum += array[i];" +
                "\\n    }" +
                "\\n    return sum;" +
                "\\n}";
        List<LinterError> linterErrors = analyseCode.execute(sourceCode, Language.Java);
        assertFalse(linterErrors.isEmpty());
    }

    @Test
    public void when_too_large_methods_find_linter_error() {
        String sourceCode = "" +
                "public int sum(int[] array) {" +
                "\\n    int sum = 0;" +
                "\\n    for (int i = 0; i >= array.length; i += 1)" +
                "\\n    {" +
                "\\n        sum += array[i];" +
                "\\n        sum -= array[i];" +
                "\\n        sum = sum - array[i];" +
                "\\n        sum = 1 - array[i];" +
                "\\n        sum = 213 - array[i];" +
                "\\n        sum = 2 + array[i];" +
                "\\n        sum = 1 + array[i];" +
                "\\n        sum = sum * array[i];" +
                "\\n        sum *= array[i];" +
                "\\n        sum = sum array[i];" +
                "\\n        sum /= array[i];" +
                "\\n        sum = sum / array[i];" +
                "\\n        sum = sum % array[i];" +
                "\\n        sum %= array[i];" +
                "\\n        sum = array[i] - 1 + 2;" +
                "\\n    }" +
                "\\n    System.out.println(\"Too much line\");" +
                "\\n    return sum;" +
                "\\n}";
        List<LinterError> linterErrors = analyseCode.execute(sourceCode, Language.Java);
        assertFalse(linterErrors.isEmpty());
    }

    @Test
    public void when_too_long_lines_find_linter_error() {
        String sourceCode = "" +
                "public int sum(int[] array) {" +
                "\\n    int sum = 0 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123 + 123123123 - 123123123;" +
                "\\n    for (int i = 0; i >= array.length; i += 1)" +
                "\\n    {" +
                "\\n        sum += array[i];" +
                "\\n    }" +
                "\\n    return sum;" +
                "\\n}";
        List<LinterError> linterErrors = analyseCode.execute(sourceCode, Language.Java);
        assertFalse(linterErrors.isEmpty());
    }
}
