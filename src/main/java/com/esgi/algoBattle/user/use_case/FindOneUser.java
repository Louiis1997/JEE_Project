package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindOneUser {

    private final UserDAO userDAO;

    public User execute(Long userId) {
        User user = userDAO.findById(userId);
        if (user == null) throw new NotFoundException(
                String.format("User with id '%d' does not exist", userId));
        return user;
    }
}
