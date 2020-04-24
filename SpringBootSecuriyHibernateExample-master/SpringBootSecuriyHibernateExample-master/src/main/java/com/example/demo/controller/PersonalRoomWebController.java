package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
@PreAuthorize("isAuthenticated()")
public class PersonalRoomWebController {

    @GetMapping("/personalroom")
    public String personalRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean admin = authorities.stream().anyMatch(userRole -> userRole.getAuthority().equals("ADMIN"));
        return admin ? "adminPersonalRoom" : "personalRoom";
    }

    @GetMapping(value = "/adminPersonalRoom/user", params = "name")
    public String getUserData(){
        return "adminPersonalRoom";
    }

    @GetMapping("/transactList")
    public String transactList() {
        return "transactionPersonalList";
    }

    @GetMapping("/transfer")
    public String transfer() {
        return "transactionWindow";
    }

    @GetMapping("/userListForAdmin")
    public String userListForAdmin(){
        return "adminUserList";
    }

    @GetMapping("/userData")
    public String findUserData(){
        return "userData";
    }
}
