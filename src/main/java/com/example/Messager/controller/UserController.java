package com.example.Messager.controller;

import com.example.Messager.entity.User;
import com.example.Messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registeUser(@RequestBody User user){
        userRepository.save(user);
        return "Пользователь успешно создан!";
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


}
