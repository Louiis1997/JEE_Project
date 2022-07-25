package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetails;
import com.esgi.algoBattle.user.infrastructure.security.CustomUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SignInTest {

    @Autowired
    private SignIn signIn;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private CustomUserDetailsService userDetailsService;


    @Test
    void should_signin() {
        String username = "Louis";
        String password = "$2a$10$HfBY9FSrWp95YyCn8GTVRudgoNWHek5itdSVDt9cyc5e7qiB.8rzW";
        CustomUserDetails customUserDetails = new CustomUserDetails(1L,"Louis","Louis.xia@gmail.com","12345", 0, null);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(customUserDetails);

        CustomUserDetails foundCustomUserDetails = signIn.execute(username, password);
        Assertions.assertEquals(customUserDetails, foundCustomUserDetails);
    }
}