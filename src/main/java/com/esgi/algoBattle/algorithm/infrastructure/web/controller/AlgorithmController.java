package com.esgi.algoBattle.algorithm.infrastructure.web.controller;

import com.esgi.algoBattle.algorithm.domain.model.Algorithm;
import com.esgi.algoBattle.algorithm.infrastructure.web.adapter.AlgorithmAdapter;
import com.esgi.algoBattle.algorithm.infrastructure.web.request.AlgorithmRequest;
import com.esgi.algoBattle.algorithm.infrastructure.web.response.AlgorithmResponse;
import com.esgi.algoBattle.algorithm.use_case.*;
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
public class AlgorithmController {

    private final AlgorithmAdapter adapter;
    private final CreateAlgorithm createAlgorithm;
    private final UpdateAlgorithm updateAlgorithm;
    private final FindAlgorithmById findAlgorithmById;
    private final FindAllAlgorithms findAllAlgorithms;
    private final DeleteAlgorithmById deleteAlgorithmById;

    @PostMapping
    public ResponseEntity<AlgorithmResponse> create(@Valid @RequestBody AlgorithmRequest request) {

        Algorithm algorithm = createAlgorithm.execute(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(algorithm.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(adapter.toResponse(algorithm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> update(
            @PathVariable("id") @NotNull @PositiveOrZero Long id,
            @Valid @RequestBody AlgorithmRequest request) {

        Algorithm algorithm = updateAlgorithm.execute(id, request);

        return ResponseEntity
                .ok()
                .body(adapter.toResponse(algorithm));
    }

    @GetMapping
    public ResponseEntity<List<AlgorithmResponse>> findAll() {
        List<Algorithm> algorithms = findAllAlgorithms.execute();
        return ResponseEntity.ok(adapter.toResponses(algorithms));
    }

    @GetMapping("/power/{power}/user/{userId}")
    public ResponseEntity<List<AlgorithmResponse>> findByPower(@PathVariable("power") @NotNull @Positive Integer power, @PathVariable("userId") @NotNull @Positive Long userId) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> findById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        Algorithm algorithm = findAlgorithmById.execute(id);
        return ResponseEntity.ok(adapter.toResponse(algorithm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> deleteById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        deleteAlgorithmById.execute(id);
        return ResponseEntity.noContent().build();
    }
}