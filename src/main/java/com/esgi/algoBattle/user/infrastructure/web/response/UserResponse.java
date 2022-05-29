package com.esgi.algoBattle.user.infrastructure.web.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
@RequiredArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
}
