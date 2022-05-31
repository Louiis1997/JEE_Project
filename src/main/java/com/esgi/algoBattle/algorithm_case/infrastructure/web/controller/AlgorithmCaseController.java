package com.esgi.algoBattle.algorithm_case.infrastructure.web.controller;

import com.esgi.algoBattle.algorithm_case.domain.model.AlgorithmCase;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.adapter.AlgorithmCaseAdapter;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.request.AlgorithmCaseRequest;
import com.esgi.algoBattle.algorithm_case.infrastructure.web.response.AlgorithmCaseResponse;
import com.esgi.algoBattle.algorithm_case.use_case.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/algorithms")
@RequiredArgsConstructor
public class AlgorithmCaseController {

    private final AlgorithmCaseAdapter adapter;
    private final CreateAlgorithmCase createAlgorithmCase;
    private final UpdateAlgorithmCase updateAlgorithmCase;
    private final FindAlgorithmCaseById findAlgorithmCaseById;
    private final FindAllAlgorithmCasesByAlgo findAllAlgorithmCasesByAlgo;
    private final DeleteAlgorithmCaseById deleteAlgorithmCaseById;

    @PostMapping("/{algoId}/cases")
    public ResponseEntity<AlgorithmCaseResponse> create(
            @PathVariable("algoId") @NotNull @Positive Long algoId,
            @Valid @RequestBody AlgorithmCaseRequest request) {

        AlgorithmCase algorithmCase = createAlgorithmCase.execute(algoId, request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{algoId}/cases/{id}")
                .buildAndExpand(algorithmCase.getAlgorithm().getId(), algorithmCase.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(adapter.toResponse(algorithmCase));
    }

    @PutMapping("/{algoId}/cases/{id}")
    public ResponseEntity<AlgorithmCaseResponse> update(
            @PathVariable("algoId") @NotNull @Positive Long algoId,
            @PathVariable("id") @NotNull @Positive Long id,
            @Valid @RequestBody AlgorithmCaseRequest request) {

        AlgorithmCase algorithmCase = updateAlgorithmCase.execute(algoId, id, request);

        return ResponseEntity
                .ok()
                .body(adapter.toResponse(algorithmCase));
    }

    @GetMapping("/{algoId}/cases")
    public ResponseEntity<List<AlgorithmCaseResponse>> findAllByAlgo(@PathVariable("algoId") @NotNull @Positive Long algoId) {
        List<AlgorithmCase> algorithmCases = findAllAlgorithmCasesByAlgo.execute(algoId);
        return ResponseEntity.ok(adapter.toResponses(algorithmCases));
    }

    @GetMapping("/{algoId}/cases/{id}")
    public ResponseEntity<AlgorithmCaseResponse> findById(
            @PathVariable("algoId") @NotNull @Positive Long algoId,
            @PathVariable("id") @NotNull @Positive Long id) {
        AlgorithmCase algorithmCase = findAlgorithmCaseById.execute(algoId, id);
        return ResponseEntity.ok(adapter.toResponse(algorithmCase));
    }

    @DeleteMapping("/{algoId}/cases/{id}")
    public ResponseEntity<AlgorithmCaseResponse> deleteById(
            @PathVariable("algoId") @NotNull @Positive Long algoId,
            @PathVariable("id") @NotNull @PositiveOrZero Long id) {
        deleteAlgorithmCaseById.execute(algoId, id);
        return ResponseEntity.noContent().build();
    }

}
