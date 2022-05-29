package com.esgi.algoBattle.user.infrastructure.web.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private Long id;
    private String name;
    private String email;

    public JWTResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

}
