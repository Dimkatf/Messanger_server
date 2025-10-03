package com.example.Messager.controller;

import com.example.Messager.entity.User;
import com.example.Messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-phone")
    public String checkPhone(@RequestParam String phone){
        boolean exists = userRepository.existsByPhone(phone);
        return exists? "EXISTS" : "NOT_EXISTS";
    }

    @PostMapping("/register")
    public String registeUser(@RequestBody User user){
        if(userRepository.existsByPhone(user.getPhone())){
            return "PHONE_EXISTS";
        }
        userRepository.save(user);
        return "Пользователь успешно создан!";
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }





}
