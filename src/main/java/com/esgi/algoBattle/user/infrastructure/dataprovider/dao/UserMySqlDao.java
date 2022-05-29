package com.esgi.algoBattle.user.infrastructure.dataprovider.dao;

import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import com.esgi.algoBattle.user.infrastructure.dataprovider.entity.UserEntity;
import com.esgi.algoBattle.user.infrastructure.dataprovider.mapper.UserMapper;
import com.esgi.algoBattle.user.infrastructure.dataprovider.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserMySqlDao implements UserDAO {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public Long registerUser(User user) {
        UserEntity newUser = userMapper.toEntity(user);
        return userRepository.save(newUser).getId();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByName(String name) {
        return Optional.ofNullable(userRepository.findByName(name))
                .map(userMapper::toDomain)
                .orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .map(userMapper::toDomain)
                .orElse(null);
    }
}
