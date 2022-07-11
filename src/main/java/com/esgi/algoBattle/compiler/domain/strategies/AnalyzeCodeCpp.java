package com.esgi.algoBattle.compiler.domain.strategies;

import com.esgi.algoBattle.compiler.domain.model.Language;
import com.esgi.algoBattle.compiler.domain.model.LinterError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AnalyzeCodeCpp implements AnalyzeCodeStrategy {

    public static final int CPP_MAX_METHOD_LINES = 20;
    public static final int CPP_MAX_LINE_CHARACTERS = 80;
    private final List<String> CPP_KEYWORDS = Arrays.asList("asm", "else", "new", "this",
            "auto", "enum", "operator", "throw",
            "bool", "explicit", "private", "true",
            "break", "export", "protected", "try",
            "case", "extern", "public", "typedef",
            "catch", "false", "register", "typeid",
            "char", "float", "reinterpret_cast", "typename",
            "class", "for", "return", "union",
            "const", "friend", "short", "unsigned",
            "const_cast", "goto", "signed", "using",
            "continue", "if", "sizeof", "virtual",
            "default", "inline", "static", "void",
            "delete", "int", "static_cast", "volatile",
            "do", "long", "struct", "wchar_t",
            "double", "mutable", "switch", "while",
            "dynamic_cast", "namespace", "template",
            "And", "bitor", "not_eq",
            "xor",
            "and_eq", "compl", "or", "xor_eq",
            "bitand", "not", "or_eq");
    private final List<String> EXCLUDED_CHARS = Arrays.asList(
            "{", "}", ";"
    );
    private final List<String> ANTI_METHODS = Arrays.asList(
            "for", "switch", "if", "else if", "catch", "try", "while"
    );
    private final int CHARACTERS_TO_ADD_ERROR = 20;

    @Override
    public Set<LinterError> findDuplicatedLines(String sourceCode) {
        var lines = separateByLines(sourceCode);
        lines = removeBlankSpaces(lines);
        final var linesWithoutSemicolons = removeSemicolons(lines);
        final var linterErrors = findDuplicatedStrings(linesWithoutSemicolons);
        findDuplicatedLinesBySemicolon(linterErrors, lines);
        return linterErrors;
    }

    private void findDuplicatedLinesBySemicolon(Set<LinterError> linterErrors, List<String> lines) {
        var lineNumber = 1;

        for (String line : lines) {
            List<String> lineParts = Arrays.asList(line.split(";"));

            findDuplicatedParts(linterErrors, lineNumber, lineParts);
            lineNumber = lineNumber + 1;
        }
    }

    private void findDuplicatedParts(Set<LinterError> linterErrors, int lineNumber, List<String> lineParts) {
        for (int i = 0; i < lineParts.size() - 1; i++) {
            for (int j = i + 1; j < lineParts.size(); j++) {
                if (lineParts.get(i).equalsIgnoreCase(lineParts.get(j))) {
                    linterErrors
                            .add(new LinterError()
                                    .setErrorNumber(1)
                                    .setErrorMessage(String.format("Line %s contains duplicated code", lineNumber)));
                }
            }
        }
    }

    private List<String> removeSemicolons(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace(";", ""))
                .collect(Collectors.toList());
    }

    private List<String> separateByLines(String sourceCode) {
        return Arrays.stream(sourceCode
                        .split(System.getProperty("line.separator")))
                .collect(Collectors.toList());
    }

    private List<String> removeBlankSpaces(List<String> lines) {
        return lines.stream()
                .map(line -> line.replace(" ", ""))
                .collect(Collectors.toList());
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

            if (CPP_KEYWORDS.contains(currentLine)) {
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
        return !EXCLUDED_CHARS.contains(line1) && !line1.isBlank() && line1.equalsIgnoreCase(line2);
    }

    private LinterError buildDuplicatedLineError(int lineNumber, int otherLineNumber) {
        return new LinterError()
                .setErrorNumber(1)
                .setErrorMessage(
                        String.format("Line %s and line %s are duplicated",
                                lineNumber,
                                otherLineNumber));
    }

    @Override
    public List<LinterError> findTooLargeMethods(String sourceCode) {
        List<String> lines = separateLines(sourceCode);
        List<LinterError> linterErrors = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            searchMethod(lines, linterErrors, i + 1);
        }

        return linterErrors;
    }

    private List<String> separateLines(String sourceCode) {

        final var linesSeparateBySemicolons = separateBySemicolons(sourceCode);
        final var linesSeparatedByOpenBrackets = separateByOpenBrackets(linesSeparateBySemicolons);
        final var linesSeparatedByCloseBrackets = separateByCloseBrackets(linesSeparatedByOpenBrackets);
        return removeBlankLines(linesSeparatedByCloseBrackets);
    }

    private List<String> removeBlankLines(List<String> lines) {
        return lines.stream()
                .filter(line -> !line.isBlank())
                .collect(Collectors.toList());
    }

    private List<String> separateByCloseBrackets(List<String> lines) {
        List<String> linesSeparatedByCloseBrackets = new ArrayList<>();
        for (String line : lines) {
            final var newLines = Arrays.asList(line.split("(?<=})"));
            linesSeparatedByCloseBrackets.addAll(newLines);
        }
        return linesSeparatedByCloseBrackets;
    }

    private List<String> separateByOpenBrackets(List<String> lines) {
        List<String> linesSeparatedByOpenBrackets = new ArrayList<>();
        for (String line : lines) {
            final var newLines = Arrays.asList(line.split("(?<=\\{)"));
            linesSeparatedByOpenBrackets.addAll(newLines);
        }
        return linesSeparatedByOpenBrackets;
    }

    private List<String> separateBySemicolons(String sourceCode) {
        return Arrays.stream(sourceCode.split(";"))
                .filter(line -> line.length() > 0)
                .collect(Collectors.toList());
    }

    private void searchMethod(List<String> lines, List<LinterError> linterErrors, int currentLineNumber) {
        final var line = lines.get(currentLineNumber - 1);

        if (isMethod(line)) {
            checkMethodLength(lines, linterErrors, currentLineNumber);
        }
    }


    private Boolean isMethod(String line) {
        final var trimLine = line.trim();
        return line.contains("(")
                && line.contains(")")
                && line.contains("{")
                && ANTI_METHODS.stream().noneMatch(trimLine::startsWith);
    }

    private void checkMethodLength(List<String> lines, List<LinterError> linterErrors, int currentLineNumber) {
        final var line = lines.get(currentLineNumber - 1);
        if (line.contains("}")) return;
        final var methodLines = countMethodLines(
                lines.subList(currentLineNumber, lines.size()));

        if (methodLines > CPP_MAX_METHOD_LINES) {
            linterErrors.add(
                    new LinterError()
                            .setErrorMessage(
                                    String.format("Method %s contains more than %s lines (%s)",
                                            getMethodName(line), CPP_MAX_METHOD_LINES, methodLines))

                            .setErrorNumber(methodLines / CPP_MAX_METHOD_LINES)
            );
        }
    }

    private String getMethodName(String line) {
        var parenthesisIndex = line.indexOf("(");
        var newLine = line.substring(0, parenthesisIndex).trim();
        var name = newLine.substring(newLine.lastIndexOf(' '));
        return name.trim();
    }

    private Integer countMethodLines(List<String> lines) {
        var length = 0;

        for (String line : lines) {
            if (line.contains("}")) {
                return length;
            }
            length = length + 1;
        }

        return length;
    }

    @Override
    public Set<LinterError> findTooLongLines(String sourceCode) {
        Set<LinterError> linterErrors = new HashSet<>();
        List<String> lines = separateByLines(sourceCode);

        var lineNumber = 1;

        for (String line : lines) {
            checkLineLength(line, lineNumber, linterErrors);
            lineNumber = lineNumber + 1;
        }

        return linterErrors;
    }

    private void checkLineLength(String line, int lineNumber, Set<LinterError> linterErrors) {
        final var lineLength = line.trim().length();

        if (lineLength > CPP_MAX_LINE_CHARACTERS) {
            final var errorNumber = calculateErrorNumber(lineLength);
            var newError = new LinterError()
                    .setErrorNumber(errorNumber)
                    .setErrorMessage(String
                            .format("Line %s contains more than %s characters (%s)",
                                    lineNumber, CPP_MAX_LINE_CHARACTERS, lineLength));
            linterErrors.add(newError);
        }
    }

    private int calculateErrorNumber(int lineLength) {
        var errorNumber = 1;
        lineLength = lineLength - CPP_MAX_LINE_CHARACTERS;
        return errorNumber + lineLength / CHARACTERS_TO_ADD_ERROR;
    }

    @Override
    public Language getStrategyLanguage() {
        return Language.Cpp;
    }
}
