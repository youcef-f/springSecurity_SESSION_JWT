package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserDao userDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUser() {
        String name = "u1";
        String pas = "pas1";
        service.saveUser(name, pas);
        verify(userDao, times(1)).saveUser(name, pas);
    }

    @Test
    public void findAllUsers() {
        String name = "u1";
        String pas = "pas1";
        User user =  buildUser(name, pas);

        String name1 = "u2";
        String pas1 = "pas1";
        User user1 =  buildUser(name1, pas1);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        when(userDao.findAllUsers()).thenReturn(userList);
        List<User> allUsers = service.findAllUsers();

        assertEquals(userList, allUsers);
    }

    @Test
    public void getUserByName() {
        String name = "u1";
        String pas = "pas1";
        User user = buildUser(name, pas);
        when(userDao.findUserByName(name)).thenReturn(user);
        User u1 = service.getUserByName("u1");
        assertEquals(name, u1.getUsername());
        assertEquals(pas, u1.getPassword());
    }

    @Test
    public void lockUser() {
        String name = "u1";
        String pas = "pas1";
        User user = buildUser(name, pas);
        user.setAccountNonLocked(true);
        when(userDao.findUserByName(name)).thenReturn(user);
        service.lockUser(user.getUsername());
        verify(userDao, times(1)).findUserByName(name);
        verify(userDao, times(1)).updateUser(user);
    }

    private User buildUser(String name, String pas){
        User user = new User(name, pas);
        List<UserRole> roles = new ArrayList<>();
//        roles.add(new UserRole(1, user, "USER"));
        user.setAuthorities(roles);
        return user;
    }
}