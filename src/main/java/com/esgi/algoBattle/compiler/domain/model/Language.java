package com.esgi.algoBattle.compiler.domain.model;

public enum Language {
    Java("java"),
    Python("py"),
    Cpp("cpp");

    private final String languageForCodex;

    Language(final String text) {
        this.languageForCodex = text;
    }

    @Override
    public String toString() {
        return languageForCodex;
    }
}