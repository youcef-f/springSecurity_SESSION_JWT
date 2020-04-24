package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/users")
    public void saveUser(@Valid @RequestBody UserDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        service.saveUser(username, password);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/person")
    public User getUserAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String name = authentication.getName();
            return service.getUserByName(name);
        } else return null;
    }

    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping("/userList")
    public List getAllUsers(){
        return service.findAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/findUser", params = "name")
    public User findUserByName(@RequestParam String name){
        User userByName = service.getUserByName(name);
        if (userByName==null) throw new UserNotFoundException();
        return userByName;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/lock", params = "name")
    public void lockUser(@RequestParam String name){
        service.lockUser(name);
    }

}
