package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.common.domain.exception.AlreadyExistsException;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUp {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public Long execute(String name, String email, String password) {
        var nameAlreadyExists = userDAO.findByName(name);
        var emailAlreadyExists = userDAO.findByEmail(email);
        checkIfNameHasAlreadyBeenTaken(nameAlreadyExists);
        checkIfEmailHasAlreadyBeenTaken(emailAlreadyExists);

        User newUser = new User()
                .setName(name)
                .setEmail(email)
                .setPassword(passwordEncoder.encode(password))
                .setLevel(0);

        return userDAO.registerUser(newUser);
    }

    private void checkIfNameHasAlreadyBeenTaken(User alreadyExists) {
        if (alreadyExists != null) {
            var message = String.format("User with name %s already exists", alreadyExists.getName());
            throw new AlreadyExistsException(message);
        }
    }

    private void checkIfEmailHasAlreadyBeenTaken(User alreadyExists) {
        if (alreadyExists != null) {
            var message = String.format("User with email %s already exists", alreadyExists.getEmail());
            throw new AlreadyExistsException(message);
        }
    }

}
