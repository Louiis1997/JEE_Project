package com.esgi.algoBattle.compiler.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

// CodexExecuteRequest is a class that represents the request to the Codex API in json format.
// This class is used to send a request to the Codex API.
public record CodexExecuteRequest(@JsonProperty("code") String code,
                                  @JsonProperty("language") String language) {
    @JsonCreator()
    public CodexExecuteRequest(String code, String language) {
        this.code = code;
        this.language = language;
    }
}
