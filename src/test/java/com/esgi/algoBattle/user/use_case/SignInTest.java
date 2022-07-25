package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.user.domain.exception.InvalidCredentialsException;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetails;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SignInTest {

    @Autowired
    private SignIn signIn;
    @MockBean
    private CustomUserDetailsService userDetailsService;


    @Test
    void should_signin() {
        String username = "Louis";
        String password = "Louis";
        CustomUserDetails customUserDetails = new CustomUserDetails(1L,"Louis","Louis.xia@gmail.com","$2a$10$HfBY9FSrWp95YyCn8GTVRudgoNWHek5itdSVDt9cyc5e7qiB.8rzW", 0, null);
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(customUserDetails);
        CustomUserDetails foundCustomUserDetails = signIn.execute(username, password);
        Assertions.assertEquals(customUserDetails, foundCustomUserDetails);
    }

    @Test
    void shoud_throw_InvalidCredentialsException() {
        String username = "Louis";
        String password = "Azertyuiop";
        CustomUserDetails customUserDetails = new CustomUserDetails(1L,"Louis","Louis.xia@gmail.com","$2a$10$HfBY9FSrWp95YyCn8GTVRudgoNWHek5itdSVDt9cyc5e7qiB.8rzW", 0, null);
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(customUserDetails);
        Assertions.assertThrows(InvalidCredentialsException.class, () -> signIn.execute(username, password));
    }
}