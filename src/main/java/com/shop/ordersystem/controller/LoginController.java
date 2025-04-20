package com.shop.ordersystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // возвращаем /WEB-INF/views/login.jsp
        return "login";
    }
}
