package com.esgi.algoBattle.user.infrastructure.web.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class SignInRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String password;
}
