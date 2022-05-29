package com.esgi.algoBattle.user.infrastructure.web.adapter;

import com.esgi.algoBattle.user.domain.model.User;
import com.esgi.algoBattle.user.infrastructure.web.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAdapter {
    public UserResponse toResponse(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    public List<UserResponse> toResponses(List<User> users) {
        return users
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
