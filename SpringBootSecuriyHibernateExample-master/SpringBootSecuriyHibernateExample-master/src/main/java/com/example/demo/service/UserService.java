package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser(String username, String password);
    void saveAdmin(String name, String password);
    List<User> findAllUsers();
    User getUserByName(String username);
    void lockUser(String name);
}
