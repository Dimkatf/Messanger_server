package com.example.Messager;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class SimpleController {
    @GetMapping("/api/test")
    public String testConnection(){
        return "Привет от Бута!";
    }
    @PostMapping("/api/echo")
    public String echoMessage(@RequestBody String message){
        return "Сообщение: " + message;
    }

}
