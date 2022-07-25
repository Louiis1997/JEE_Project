package com.esgi.algoBattle.user.domain.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer level;
}
