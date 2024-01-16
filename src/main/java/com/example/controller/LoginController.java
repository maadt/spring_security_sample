package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginForm")
    public String getLogin() {
    //getLogin()：loginForm.htmlを格納して返す
        return "loginForm";
    }
}