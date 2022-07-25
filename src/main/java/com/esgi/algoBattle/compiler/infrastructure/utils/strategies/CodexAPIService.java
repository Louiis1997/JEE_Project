package com.esgi.algoBattle.compiler.infrastructure.utils.strategies;

import com.esgi.algoBattle.compiler.infrastructure.models.CodexExecuteRequest;
import com.esgi.algoBattle.compiler.infrastructure.models.CodexExecuteResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

@Service
public class CodexAPIService {
    public String url = "https://codex-api.herokuapp.com/";

    public CodexExecuteResponse post(String code, String language) {
        WebClient webClient = WebClient.builder().baseUrl(url).build();
        return webClient.post()
                .uri(UriBuilder::build)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(new CodexExecuteRequest(code, language)), CodexExecuteRequest.class)
                .retrieve()
                .bodyToMono(CodexExecuteResponse.class)
                .block();
    }
}
