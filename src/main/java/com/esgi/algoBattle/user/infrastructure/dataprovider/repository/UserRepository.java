package com.esgi.algoBattle.user.infrastructure.dataprovider.repository;

import com.esgi.algoBattle.user.infrastructure.dataprovider.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByName(String name);

    UserEntity findByEmail(String email);
}
