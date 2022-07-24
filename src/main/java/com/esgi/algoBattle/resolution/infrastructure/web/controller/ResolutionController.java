package com.esgi.algoBattle.resolution.infrastructure.web.controller;

import com.esgi.algoBattle.compiler.domain.model.LinterError;
import com.esgi.algoBattle.game.use_case.CheckGameStatus;
import com.esgi.algoBattle.player.infrastructure.web.adapter.PlayerAdapter;
import com.esgi.algoBattle.player.use_case.RetrievePlayersByGame;
import com.esgi.algoBattle.player.use_case.RetrievePlayersByGames;
import com.esgi.algoBattle.resolution.domain.model.Resolution;
import com.esgi.algoBattle.resolution.infrastructure.web.adapter.ResolutionAdapter;
import com.esgi.algoBattle.resolution.infrastructure.web.adapter.ResolutionByGameAdapter;
import com.esgi.algoBattle.resolution.infrastructure.web.adapter.ResolutionsByAlgoAdapter;
import com.esgi.algoBattle.resolution.infrastructure.web.adapter.ResolutionsByUserAdapter;
import com.esgi.algoBattle.resolution.infrastructure.web.request.ResolutionRequest;
import com.esgi.algoBattle.resolution.infrastructure.web.request.StartedResolutionRequest;
import com.esgi.algoBattle.resolution.infrastructure.web.response.*;
import com.esgi.algoBattle.resolution.use_case.*;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResolutionController {

    private final ResolutionAdapter resolutionAdapter;
    private final ResolutionsByAlgoAdapter resolutionsByAlgoAdapter;
    private final ResolutionsByUserAdapter resolutionsByUserAdapter;
    private final ResolutionByGameAdapter resolutionByGameAdapter;
    private final SaveResolution saveResolution;
    private final DeleteResolution deleteResolution;
    private final FindAllResolutionsByAlgo findAllResolutionsByAlgorithm;
    private final FindAllResolutionsByUser findAllResolutionsByUser;
    private final FindAllResolutionsByGame findAllResolutionsByGame;
    private final FindOneResolution findOneResolution;
    private final OpponentLosesHealth opponentLosesHealth;
    private final CheckGameStatus checkGameStatus;
    private final StartResolution startResolution;
    private final RetrievePlayersByGame retrievePlayersByGame;
    private final PlayerAdapter playerAdapter;
    private final RetrievePlayersByGames retrievePlayersByGames;

    @PostMapping("/resolutions/start")
    public ResponseEntity<ResolutionResponse> addStartedResolution(
            @Valid @RequestBody StartedResolutionRequest request) {

        final var loggedUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final var resolution = startResolution.execute(loggedUser.getId(),
                request.getAlgorithmId(), request.getGameId());

        return ResponseEntity
                .ok()
                .body(resolutionAdapter.toResponse(resolution));
    }

    @PutMapping("/resolutions")
    public ResponseEntity<ResolutionResponse> addResolution(
            @Valid @RequestBody ResolutionRequest request) {

        final var loggedUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var userId = loggedUser.getId();

        final var resolution = findOneResolution.execute(userId, request.getAlgorithmId());
        final var resolutionTime = resolution.getStartedTime().until(LocalDateTime.now(), ChronoUnit.SECONDS);

        return saveResolution(userId,
                request.getAlgorithmId(),
                resolution.getGame().getId(),
                resolutionTime,
                request.getSolved(),
                request.getLinterErrors());
    }

    private ResponseEntity<ResolutionResponse> saveResolution(Long userId, Long algorithmId, Long gameId, Long resolutionTime,
                                                              Boolean solved, List<LinterError> linterErrors) {

        final var resolution = saveResolution.execute(userId,
                algorithmId,
                gameId,
                resolutionTime,
                solved,
                linterErrors);

        final var errorNumber = linterErrors.stream()
                .mapToInt(LinterError::getErrorNumber)
                .sum();

        if (Boolean.TRUE.equals(resolution.isSolved())) {
            opponentLosesHealth.execute(gameId, userId, errorNumber);
        }

        checkGameStatus.execute(gameId);

        var player = retrievePlayersByGame.execute(gameId).stream().filter(p -> p.getUser().getId().equals(userId))
                .map(playerAdapter::toResponse)
                .findAny()
                .orElse(null);

        return ResponseEntity
                .ok()
                .body(resolutionAdapter.toResponse(resolution).setPlayer(player));
    }

    @DeleteMapping("/users/{userId}/algorithms/{algorithmId}")
    public ResponseEntity<ResolutionResponse> deleteAlgorithmFromUser(
            @PathVariable("userId") @NotNull @Positive Long userId,
            @PathVariable("algorithmId") @NotNull @Positive Long algorithmId) {

        deleteResolution.execute(userId, algorithmId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/algorithms/{algorithmId}/users/{userId}")
    public ResponseEntity<ResolutionResponse> deleteUserFromAlgorithm(
            @PathVariable("userId") @NotNull @Positive Long userId,
            @PathVariable("algorithmId") @NotNull @Positive Long algorithmId) {

        deleteResolution.execute(userId, algorithmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/algorithms/{algorithmId}/users")
    public ResponseEntity<List<ResolutionsByAlgoResponse>> findAllUsersByAlgorithm(
            @PathVariable("algorithmId") @NotNull @Positive Long algorithmId) {

        List<Resolution> resolutions = findAllResolutionsByAlgorithm.execute(algorithmId);
        return ResponseEntity.ok(resolutionsByAlgoAdapter.toResponses(resolutions));
    }

    @GetMapping("/users/{userId}/algorithms")
    public ResponseEntity<List<ResolutionsByUserResponse>> findAllAlgorithmsByUser(
            @PathVariable("userId") @NotNull @Positive Long userId) {

        List<Resolution> resolutions = findAllResolutionsByUser.execute(userId);
        return ResponseEntity.ok(resolutionsByUserAdapter.toResponses(resolutions));
    }

    @GetMapping("/games/{gameId}/algorithms")
    public ResponseEntity<List<ResolutionsByGameResponse>> findAllAlgorithmsByGame(
            @PathVariable("gameId") @NotNull @Positive Long gameId) {

        List<Resolution> resolutions = findAllResolutionsByGame.execute(gameId);
        return ResponseEntity.ok(resolutionByGameAdapter.toResponses(resolutions));
    }

    @GetMapping("/resolutions/users/{userId}")
    public ResponseEntity<List<ResolutionDetailsResponse>> findDetailsByUser(
            @PathVariable("userId") @NotNull @Positive Long userId) {

        final var resolutions = findAllResolutionsByUser.execute(userId);
        final var gameIds = resolutions.stream()
                .map(resolution -> resolution.getGame().getId())
                .collect(Collectors.toList());
        final var players = retrievePlayersByGames.execute(gameIds);

        return ResponseEntity.ok(resolutionAdapter.toDetailResponses(resolutions, players));
    }
}
