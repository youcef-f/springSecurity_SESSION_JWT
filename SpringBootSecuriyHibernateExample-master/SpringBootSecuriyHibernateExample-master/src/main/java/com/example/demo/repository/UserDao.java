package com.example.demo.repository;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao {


    User findUserByName(String name) throws UserNotFoundException;

    void saveUser(String username, String password);

    List<User> findAllUsers();

    void saveAdmin(String username, String password);

    void updateUser(User userGetter);
}
