package com.example.demo.service.impl;

import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.UserDao;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    private UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String s) {
        try {
            User userByName = dao.findUserByName(s);
            Hibernate.initialize(userByName.getAuthorities());
            return userByName;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveUser(@NotNull String username, @NotNull String password) {
        String userByName;
        try {
            userByName = dao.findUserByName(username).getUsername();
            if (userByName.equals(username)) throw new UserAlreadyExistException();
        } catch (NullPointerException | UserNotFoundException ignored){
            dao.saveUser(username, password);
        }
    }

    @Override
    public void saveAdmin(String name, String password) {
        dao.saveAdmin(name,password);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = dao.findAllUsers();
        return allUsers;
    }

    @Override
    public User getUserByName(String username){
        User userByName = null;
        try {
            userByName = dao.findUserByName(username);
        } catch (UserNotFoundException e) {
            System.out.println("User " + username + " was not found");
        }
        return userByName;
    }

    @Override
    public void lockUser(String name) {
        User userByName = dao.findUserByName(name);
        boolean locked = userByName.isAccountNonLocked();
        userByName.setAccountNonLocked(!locked);
        dao.updateUser(userByName);
    }
}
