package com.esgi.algoBattle.game.infrastructure.web.controller;

import com.esgi.algoBattle.game.domain.model.Game;
import com.esgi.algoBattle.game.infrastructure.web.adapter.GameAdapter;
import com.esgi.algoBattle.game.infrastructure.web.request.GameCreateRequest;
import com.esgi.algoBattle.game.infrastructure.web.request.GameUpdateRequest;
import com.esgi.algoBattle.game.infrastructure.web.response.GameResponse;
import com.esgi.algoBattle.game.use_case.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameAdapter adapter;
    private final CreateGame createGame;
    private final UpdateGame updateGame;
    private final FindGameById findGameById;
    private final FindAllGames findAllGames;
    private final FindAllUnfinishedGames findAllUnfinishedGames;
    private final DeleteGameById deleteGameById;

    @PostMapping
    public ResponseEntity<GameResponse> create(@Valid @RequestBody GameCreateRequest request) {
        System.out.println("request: " + request);
        Game game = createGame.execute(request.getDate(), request.getOver());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(game.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(adapter.toResponse(game));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> update(
            @PathVariable("id") @NotNull @PositiveOrZero Long id,
            @Valid @RequestBody GameUpdateRequest request) {

        Game game = updateGame.execute(id, request.getOver());

        return ResponseEntity
                .ok()
                .body(adapter.toResponse(game));
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> findAll() {
        List<Game> games = findAllGames.execute();
        return ResponseEntity.ok(adapter.toResponses(games));
    }

    @GetMapping("/unfinished")
    public ResponseEntity<List<GameResponse>> findAllUnfinishedGames() {
        List<Game> games = findAllUnfinishedGames.execute();
        return ResponseEntity.ok(adapter.toResponses(games));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> findById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        Game game = findGameById.execute(id);
        return ResponseEntity.ok(adapter.toResponse(game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GameResponse> deleteById(@PathVariable("id") @NotNull @PositiveOrZero Long id) {
        deleteGameById.execute(id);
        return ResponseEntity.noContent().build();
    }

}
