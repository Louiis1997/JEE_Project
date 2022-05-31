package com.esgi.algoBattle.player.infrastructure.web.controller;

import com.esgi.algoBattle.player.domain.model.Player;
import com.esgi.algoBattle.player.infrastructure.web.adapter.GamesByUserAdapter;
import com.esgi.algoBattle.player.infrastructure.web.adapter.PlayerAdapter;
import com.esgi.algoBattle.player.infrastructure.web.adapter.UsersByGameAdapter;
import com.esgi.algoBattle.player.infrastructure.web.request.PlayerRequest;
import com.esgi.algoBattle.player.infrastructure.web.response.GamesByUserResponse;
import com.esgi.algoBattle.player.infrastructure.web.response.PlayerResponse;
import com.esgi.algoBattle.player.infrastructure.web.response.UsersByGameResponse;
import com.esgi.algoBattle.player.use_case.AddOrUpdatePlayer;
import com.esgi.algoBattle.player.use_case.RemovePlayer;
import com.esgi.algoBattle.player.use_case.RetrievePlayersByGame;
import com.esgi.algoBattle.player.use_case.RetrievePlayersByUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerAdapter playerAdapter;
    private final UsersByGameAdapter usersByGameAdapter;
    private final GamesByUserAdapter gamesByUserAdapter;
    private final AddOrUpdatePlayer addOrUpdatePlayer;
    private final RemovePlayer removePlayer;
    private final RetrievePlayersByUser retrievePlayersByUser;
    private final RetrievePlayersByGame retrievePlayersByGame;

    @PutMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<PlayerResponse> addOrUpdateUserToGame(
            @PathVariable("gameId") @NotNull @Positive Long gameId,
            @PathVariable("userId") @NotNull @Positive Long userId,
            @Valid @RequestBody PlayerRequest request) {
        Player player = addOrUpdatePlayer.execute(gameId, userId, request);
        return ResponseEntity
                .ok()
                .body(playerAdapter.toResponse(player));
    }

    @PutMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<PlayerResponse> addOrUpdateGameToUser(
            @PathVariable("gameId") @NotNull @Positive Long gameId,
            @PathVariable("userId") @NotNull @Positive Long userId,
            @Valid @RequestBody PlayerRequest request) {
        Player player = addOrUpdatePlayer.execute(gameId, userId, request);
        return ResponseEntity
                .ok()
                .body(playerAdapter.toResponse(player));
    }

    @DeleteMapping("/games/{gameId}/users/{userId}")
    public ResponseEntity<PlayerResponse> deleteUserFromGame(
            @PathVariable("gameId") @NotNull @Positive Long gameId,
            @PathVariable("userId") @NotNull @Positive Long userId) {
        removePlayer.execute(gameId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<PlayerResponse> deleteGameFromUser(
            @PathVariable("gameId") @NotNull @Positive Long gameId,
            @PathVariable("userId") @NotNull @Positive Long userId) {
        removePlayer.execute(gameId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/games")
    public ResponseEntity<List<GamesByUserResponse>> findAllGamesByUser(
            @PathVariable("userId") @NotNull @Positive Long userId) {
        List<Player> players = retrievePlayersByUser.execute(userId);
        return ResponseEntity.ok(gamesByUserAdapter.toResponses(players));
    }

    @GetMapping("/games/{gameId}/users")
    public ResponseEntity<List<UsersByGameResponse>> findAllUsersByGame(
            @PathVariable("gameId") @NotNull @Positive Long gameId) {
        List<Player> players = retrievePlayersByGame.execute(gameId);
        return ResponseEntity.ok(usersByGameAdapter.toResponses(players));
    }


}
