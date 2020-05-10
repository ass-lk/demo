package com.ass.cloud.controller;

import com.ass.cloud.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") Long id){
        return new User(1L,"皮皮","123456");
    }


}
