package com.esgi.algoBattle.user.infrastructure.web.controller;

import com.esgi.algoBattle.user.domain.model.User;
import com.esgi.algoBattle.user.infrastructure.security.JWTTokenUtil;
import com.esgi.algoBattle.user.infrastructure.web.adapter.UserAdapter;
import com.esgi.algoBattle.user.infrastructure.web.request.SignInRequest;
import com.esgi.algoBattle.user.infrastructure.web.request.SignUpRequest;
import com.esgi.algoBattle.user.infrastructure.web.response.JWTResponse;
import com.esgi.algoBattle.user.infrastructure.web.response.UserResponse;
import com.esgi.algoBattle.user.use_case.FindOneUser;
import com.esgi.algoBattle.user.use_case.SignIn;
import com.esgi.algoBattle.user.use_case.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Validated
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserAdapter userAdapter;
    private final SignUp signUp;
    private final SignIn signIn;
    private final FindOneUser findOneUser;
    private final JWTTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest request) {
        Long userId = signUp.execute(request.getName(), request.getEmail(),
                request.getPassword());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponse> login(@Valid @RequestBody SignInRequest request) {

        final var user = signIn.execute(request.getName(), request.getPassword());
        final var token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new JWTResponse(token).setEmail(user.getEmail())
                .setName(user.getUsername()).setId(user.getId()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long userId) {
        User user = findOneUser.execute(userId);
        return ResponseEntity.ok(userAdapter.toResponse(user));
    }
}
