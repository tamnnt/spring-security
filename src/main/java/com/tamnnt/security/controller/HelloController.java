package com.tamnnt.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "welcome to shop ban trang suc!";
    }
//    @GetMapping("/login")
//    public String login(){
//        return "login";
//    }
    @GetMapping("/hidden")
    public String hidden(){
        return "Just see this, when login!";
    }
}
