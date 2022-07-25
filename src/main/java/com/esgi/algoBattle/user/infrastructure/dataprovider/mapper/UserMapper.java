package com.esgi.algoBattle.user.infrastructure.dataprovider.mapper;

import com.esgi.algoBattle.user.domain.model.User;
import com.esgi.algoBattle.user.infrastructure.dataprovider.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        return new User()
                .setId(entity.getId())
                .setName(entity.getName())
                .setEmail(entity.getEmail())
                .setPassword(entity.getPassword())
                .setLevel(entity.getLevel());
    }

    public UserEntity toEntity(User user) {
        return new UserEntity()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setLevel(user.getLevel());
    }
}
