package com.esgi.algoBattle.compiler.domain.strategies;

import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyzeCodePython implements AnalyzeCodeStrategy {

    public static final int PYTHON_MAX_METHOD_LINES = 20;
    private final List<String> PYTHON_KEYWORDS =
            Arrays.asList("'''", "False", "None", "True", "and", "as", "assert", "async", "await", "break", "class", "continue", "def", "del", "elif", "else", "except", "finally", "for", "from", "global", "if", "import", "in", "is", "lambda", "nonlocal", "not", "or", "pass", "raise", "return", "try", "while", "with", "yield");

    @Override
    public Set<LinterError> findDuplicatedLines(String sourceCode) {
        List<String> lines = separateByLines(sourceCode);
        return findDuplicatedStrings(lines);
    }

    private Set<LinterError> findDuplicatedStrings(List<String> lines) {
        Set<LinterError> linterErrors = new HashSet<>();

        for (int i = 0; i < lines.size() - 1; i++) {

            for (int j = i + 1; j < lines.size(); j++) {
                checkIfLinesAreEquals(lines, linterErrors, i, j);
            }
        }

        return linterErrors;
    }

    private void checkIfLinesAreEquals(List<String> lines, Set<LinterError> linterErrors, int i, int j) {
        final var currentLine = lines.get(i);
        final var lineNumber = i + 1;
        final var otherLine = lines.get(j);

        if (linesAreEquals(currentLine, otherLine)) {

            if (PYTHON_KEYWORDS.contains(currentLine)) {
                checkPreviousAndNextLines(linterErrors, lines, i, j);
            } else {
                final var duplicatedLineNumber = j + 1;
                linterErrors
                        .add(buildDuplicatedLineError(lineNumber, duplicatedLineNumber));
            }
        }
    }

    private void checkPreviousAndNextLines(Set<LinterError> linterErrors, List<String> lines, int i, int j) {
        final var lineNumber = i + 1;
        final var otherLineNumber = j + 1;

        if (checkPreviousLines(linterErrors, lines, i, j, lineNumber, otherLineNumber)) return;
        checkNextLines(linterErrors, lines, i, j, lineNumber, otherLineNumber);
    }

    private boolean checkPreviousLines(Set<LinterError> linterErrors, List<String> lines, int i, int j, int lineNumber, int otherLineNumber) {
        final var minimumIndex = 0;
        if (i > minimumIndex) {

            final var previousLine = lines.get(i - 1);
            final var otherPreviousLine = lines.get(j - 1);

            if (linesAreEquals(previousLine, otherPreviousLine)) {
                linterErrors
                        .add(buildDuplicatedLineError(lineNumber, otherLineNumber));
                return true;
            }
        }
        return false;
    }

    private void checkNextLines(Set<LinterError> linterErrors, List<String> lines, int i, int j, int lineNumber, int otherLineNumber) {
        final var maximumIndex = lines.size() - 1;
        if (j < maximumIndex) {

            final var nextLine = lines.get(i + 1);
            final var otherNextLine = lines.get(j + 1);

            if (linesAreEquals(nextLine, otherNextLine)) {
                linterErrors
                        .add(buildDuplicatedLineError(lineNumber, otherLineNumber));
            }
        }
    }

    private boolean linesAreEquals(String line1, String line2) {
        return !line1.isBlank() && line1.equalsIgnoreCase(line2);
    }

    private LinterError buildDuplicatedLineError(int lineNumber, int otherLineNumber) {
        return new LinterError()
                .setErrorNumber(1)
                .setErrorMessage(
                        String.format("Line %s and line %s are duplicated",
                                lineNumber,
                                otherLineNumber));
    }

    private List<String> separateByLines(String sourceCode) {
        return Arrays.stream(sourceCode
                        .split(System.getProperty("line.separator")))
                .map(line -> line.replace(" ", ""))
                .collect(Collectors.toList());
    }


    @Override
    public List<LinterError> findTooLargeMethods(String sourceCode) {
        List<String> lines = separateByLinesWithSpaces(sourceCode);
        List<LinterError> linterErrors = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            searchMethod(lines, linterErrors, i + 1);
        }

        return linterErrors;
    }

    private void searchMethod(List<String> lines, List<LinterError> linterErrors, int currentLineNumber) {
        final var line = lines.get(currentLineNumber - 1);
        final var totalLineNumber = lines.size();

        if (line.startsWith("def ") && totalLineNumber - currentLineNumber > PYTHON_MAX_METHOD_LINES) {
            checkMethodLength(lines, linterErrors, currentLineNumber);
        }
    }

    private void checkMethodLength(List<String> lines, List<LinterError> linterErrors, int currentLineNumber) {
        final var line = lines.get(currentLineNumber - 1);
        final var methodLines = countMethodLines(
                lines.subList(currentLineNumber, lines.size()));

        if (methodLines > PYTHON_MAX_METHOD_LINES) {
            linterErrors.add(
                    new LinterError()
                            .setErrorMessage(
                                    String.format("Method %s contains more than %s lines (%s)",
                                            getMethodName(line), PYTHON_MAX_METHOD_LINES, methodLines))

                            .setErrorNumber(methodLines / PYTHON_MAX_METHOD_LINES)
            );
        }
    }

    private List<String> separateByLinesWithSpaces(String sourceCode) {
        return Arrays.stream(sourceCode.split(System.getProperty("line.separator")))
                .filter(line -> line.length() > 0)
                .collect(Collectors.toList());
    }

    private String getMethodName(String line) {
        return line
                .replace(":", "")
                .replace("def", "")
                .replace("(", "")
                .replace(")", "")
                .trim();
    }

    private Integer countMethodLines(List<String> lines) {
        var length = 0;

        for (String line : lines) {
            var spaces = countSpacesBeforeFirstChar(line);
            if (spaces == 0) {
                return length;
            }
            length = length + 1;
        }

        return length;
    }

    private Integer countSpacesBeforeFirstChar(String line) {
        var spaces = 0;

        for (char character : line.toCharArray()) {
            if (character != ' ') {
                return spaces;
            }
            spaces = spaces + 1;
        }

        return spaces;
    }

    @Override
    public Set<LinterError> findTooLongLines(String sourceCode) {
        return new HashSet<>();
    }

    @Override
    public Language getStrategyLanguage() {
        return Language.Python;
    }
}
