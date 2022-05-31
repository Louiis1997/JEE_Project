package com.esgi.algoBattle.case_input.infrastructure.web.controller;

import com.esgi.algoBattle.case_input.domain.model.CaseInput;
import com.esgi.algoBattle.case_input.infrastructure.web.adapter.CaseInputAdapter;
import com.esgi.algoBattle.case_input.infrastructure.web.request.CaseInputRequest;
import com.esgi.algoBattle.case_input.infrastructure.web.response.CaseInputResponse;
import com.esgi.algoBattle.case_input.use_case.*;
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
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseInputController {

    private final CaseInputAdapter adapter;
    private final CreateCaseInput createCaseInput;
    private final UpdateCaseInput updateCaseInput;
    private final FindCaseInputById findCaseInputById;
    private final FindAllCaseInputsByCase findAllCaseInputsByCase;
    private final DeleteCaseInputById deleteCaseInputById;

    @PostMapping("/{caseId}/input")
    public ResponseEntity<CaseInputResponse> create(
            @PathVariable("caseId") @NotNull @Positive Long caseId,
            @Valid @RequestBody CaseInputRequest request) {

        CaseInput caseInput = createCaseInput.execute(caseId, request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{caseId}/input/{id}")
                .buildAndExpand(caseInput.getAlgorithmCase().getId(), caseInput.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(adapter.toResponse(caseInput));
    }

    @PutMapping("/{caseId}/input/{id}")
    public ResponseEntity<CaseInputResponse> update(
            @PathVariable("caseId") @NotNull @Positive Long caseId,
            @PathVariable("id") @NotNull @Positive Long id,
            @Valid @RequestBody CaseInputRequest request) {

        CaseInput caseInput = updateCaseInput.execute(caseId, id, request);

        return ResponseEntity
                .ok()
                .body(adapter.toResponse(caseInput));
    }

    @GetMapping("/{caseId}/input")
    public ResponseEntity<List<CaseInputResponse>> findAllByCase(@PathVariable("caseId") @NotNull @Positive Long caseId) {
        List<CaseInput> caseInputs = findAllCaseInputsByCase.execute(caseId);
        return ResponseEntity.ok(adapter.toResponses(caseInputs));
    }

    @GetMapping("/{caseId}/input/{id}")
    public ResponseEntity<CaseInputResponse> findById(
            @PathVariable("caseId") @NotNull @Positive Long caseId,
            @PathVariable("id") @NotNull @Positive Long id) {
        CaseInput caseInput = findCaseInputById.execute(caseId, id);
        return ResponseEntity.ok(adapter.toResponse(caseInput));
    }

    @DeleteMapping("/{caseId}/input/{id}")
    public ResponseEntity<CaseInputResponse> deleteById(
            @PathVariable("caseId") @NotNull @Positive Long caseId,
            @PathVariable("id") @NotNull @PositiveOrZero Long id) {
        deleteCaseInputById.execute(caseId, id);
        return ResponseEntity.noContent().build();
    }

}
