package com.esgi.algoBattle.user.infrastructure.security;

import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findByName(s);

        if (user == null) {
            throw new UsernameNotFoundException(s);
        }

        return CustomUserDetails.build(user);
    }
}
