package com.example.controller;

import com.example.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DemoController {

    @GetMapping("/world")
    public User helloWorld(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }


}
