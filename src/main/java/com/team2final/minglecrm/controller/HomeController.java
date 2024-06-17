package com.team2final.minglecrm.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }
}
