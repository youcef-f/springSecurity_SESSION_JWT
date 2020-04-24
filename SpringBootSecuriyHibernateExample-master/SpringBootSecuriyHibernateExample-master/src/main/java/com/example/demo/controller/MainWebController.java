package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainWebController {

    @GetMapping(value = "/registration")
    public String registrationPage() {
        return "registration";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String homePage() {
        return "index";
    }
}
