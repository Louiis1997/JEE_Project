package com.esgi.algoBattle.user.domain.dao;


import com.esgi.algoBattle.user.domain.model.User;

public interface UserDAO {
    Long registerUser(User user);

    User findById(Long userId);

    User findByName(String name);

    User findByEmail(String email);
}
