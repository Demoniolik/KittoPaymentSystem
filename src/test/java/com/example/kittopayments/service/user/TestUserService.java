package com.example.kittopayments.service.user;

import com.example.paymentsystem.dao.user.UserDao;
import com.example.paymentsystem.exception.DatabaseException;
import com.example.paymentsystem.model.user.User;
import com.example.paymentsystem.service.user.UserService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TestUserService {
    @Mock
    private UserDao userDao;

    private UserService userService;

    @Before
    public void init() {
        userService = new UserService(userDao);
    }


    @Test
    public void givenUser_whenCallingRegisterUser_shouldReturnTrue() {
        User user = mock(User.class);
        User registered = mock(User.class);
        registered.setId(1L);

        try {
            when(userDao.save(user)).thenReturn(registered);
        } catch (DatabaseException e) {
            fail();
        }

        Boolean test = null;
        try {
            test = userService.register(user);
            verify(userDao, only()).save(user);
        } catch (DatabaseException e) {
            fail();
        }

        Assert.assertTrue(test);
    }

    @Test
    public void givenNull_whenCallingRegisterUser_shouldReturnFalse() {
        Boolean test = null;
        try {
            test = userService.register(null);
        } catch (DatabaseException e) {
            fail();
        }

        try {
            verify(userDao, never()).save(any());
        } catch (DatabaseException e) {
            fail();
        }
        Assert.assertFalse(test);
    }

    @Test
    public void givenCredentials_whenCallingGetUserByCredentials_shouldReturnUser() throws DatabaseException {
        User user = mock(User.class);
        when(userDao.getUserByEmailAndPassword("email", "password")).thenReturn(user);

        User test = userService.getUserByCredentials("email", "password");

        verify(userDao, only()).getUserByEmailAndPassword(anyString(), anyString());
        Assert.assertNotNull(test);
    }

    @Test
    public void givenNullCredentials_whenCallingGetUserByCredentials_shouldReturnNull() throws DatabaseException {
        User test = userService.getUserByCredentials("email", null);

        verify(userDao, never()).getUserByEmailAndPassword(anyString(), anyString());
        Assert.assertNull(test);
    }

    @Test
    public void givenValidId_whenCallingFindUserById_shouldReturnUser() {
        User user = mock(User.class);
        try {
            when(userDao.get(1L)).thenReturn(user);
        } catch (DatabaseException e) {
            fail();
        }

        User test = null;
        try {
            test = userService.getUserById(1L);
        } catch (DatabaseException e) {
            fail();
        }

        try {
            verify(userDao, only()).get(anyLong());
        } catch (DatabaseException e) {
            fail();
        }
        Assert.assertNotNull(test);
    }

    @Test
    public void givenInvalidId_whenCallingFindUserById_shouldReturnNull() {
        final long WRONG_USER_ID = 1919191L;

        try {
            when(userDao.get(WRONG_USER_ID)).thenReturn(null);
        } catch (DatabaseException e) {
            fail();
        }

        User test = null;
        try {
            test = userService.getUserById(WRONG_USER_ID);
            verify(userDao, only()).get(anyLong());
        } catch (DatabaseException e) {
            fail();
        }

        Assert.assertNull(test);
    }

    @Test
    public void getSpecifiedUserNameByCardId_shouldReturnTrue() {
        final long CARD_ID = 1;
        final String expectedSpecifiedUsername = "Bekker D.";

        try {
            when(userDao.getUserSpecifiedNameByCardId(CARD_ID)).thenReturn(expectedSpecifiedUsername);
        } catch (DatabaseException e) {
            fail();
        }

        String actual = null;
        try {
            actual = userService.getSpecifiedUserNameByCardId(CARD_ID);
            verify(userDao, only()).getUserSpecifiedNameByCardId(anyLong());
        } catch (DatabaseException e) {
            fail();
        }

        assertEquals(expectedSpecifiedUsername, actual);

    }

}
