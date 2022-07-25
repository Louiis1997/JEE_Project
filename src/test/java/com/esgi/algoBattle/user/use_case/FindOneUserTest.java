package com.esgi.algoBattle.user.use_case;

import com.esgi.algoBattle.common.domain.exception.NotFoundException;
import com.esgi.algoBattle.user.domain.dao.UserDAO;
import com.esgi.algoBattle.user.domain.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class FindOneUserTest {

    @Autowired
    private FindOneUser findOneUser;

    @MockBean
    private UserDAO userDAO;

    @Test
    public void should_found_an_user(){
        User user = new User()
                .setId(1L)
                .setName("Louis")
                .setEmail("Louise.xia@gmail.com")
                .setPassword("Louis")
                .setLevel(0);
        Mockito.when(userDAO.findById(user.getId())).thenReturn(user);

        User foundUser = findOneUser.execute(user.getId());

        verify(userDAO, times(1)).findById(user.getId());
        Assertions.assertEquals(user, foundUser);
    }

    @Test
    void should_throw_a_not_found_exception() {
        Long userId = 0L;
        Mockito.when(userDAO.findById(userId)).thenThrow(new NotFoundException(
                String.format("User with id '%d' does not exist", 0L)));

        Assertions.assertThrows(NotFoundException.class, () -> findOneUser.execute(userId));

        verify(userDAO, times(1)).findById(userId);
    }
}