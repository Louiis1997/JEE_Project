package com.esgi.algoBattle.user.infrastructure.dataprovider.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity(name = "user")
@Accessors(chain = true)
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;
}
