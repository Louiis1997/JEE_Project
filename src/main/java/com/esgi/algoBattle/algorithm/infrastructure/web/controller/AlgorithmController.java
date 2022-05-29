package com.esgi.algoBattle.algorithm.infrastructure.web.controller;

import com.esgi.algoBattle.algorithm.infrastructure.web.request.AlgorithmRequest;
import com.esgi.algoBattle.algorithm.infrastructure.web.response.AlgorithmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/algorithms")
@RequiredArgsConstructor
public class AlgorithmController {

    @PostMapping
    public ResponseEntity<AlgorithmResponse> create(@Valid @RequestBody AlgorithmRequest request) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> update(
            @PathVariable("id") @NotNull @PositiveOrZero Long id,
            @Valid @RequestBody AlgorithmRequest request) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<AlgorithmResponse>> findAll() {
        return null;
    }

    @GetMapping("/power/{power}/user/{userId}")
    public ResponseEntity<List<AlgorithmResponse>> findByPower(@PathVariable("power") @NotNull @Positive Integer power, @PathVariable("userId") @NotNull @Positive Long userId) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> findById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlgorithmResponse> deleteById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        return null;
    }
}