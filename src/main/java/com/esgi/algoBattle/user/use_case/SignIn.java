package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.user.domain.exception.InvalidCredentialsException;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetails;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignIn {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public CustomUserDetails execute(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new InvalidCredentialsException("Wrong credentials");
        }

        return (CustomUserDetails) userDetailsService
                .loadUserByUsername(username);
    }
}
