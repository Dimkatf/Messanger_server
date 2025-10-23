package com.example.Messager.service;

import com.example.Messager.entity.User; // ✅ ПРАВИЛЬНЫЙ ИМПОРТ!
import com.example.Messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void updateUserName(String phone, String newName) {
        System.out.println("🔧🔧🔧 USER SERVICE CALLED 🔧🔧🔧");
        System.out.println("Phone: '" + phone + "'");
        System.out.println("New Name: '" + newName + "'");

        System.out.println("Searching user by phone...");
        User user = userRepository.findByPhone(phone);
        System.out.println("User found: " + (user != null));

        if (user != null) {
            System.out.println("Current name: " + user.getName());
            System.out.println("Setting new name...");
            user.setName(newName);

            System.out.println("Saving user...");
            userRepository.save(user);
            System.out.println("✅ User saved successfully!");
        } else throw new RuntimeException("User not found with phone: " + phone);

    }
    public void addUserName(String phone, String userName) {
        System.out.println("🔍 addUserName called - phone: " + phone + ", username: " + userName);

        User user = userRepository.findByPhone(phone);
        if (user != null) {
            System.out.println("🔍 User found: " + user.getId() + ", current username: " + user.getUserName());
            user.setUserName(userName);
            userRepository.save(user);

            System.out.println("🔍 After save - new username: " + user.getUserName());

            User updatedUser = userRepository.findByPhone(phone);
            System.out.println("🔍 Verification - username in DB: " + updatedUser.getUserName());
        } else {
            System.out.println("❌ User not found with phone: " + phone);
            throw new RuntimeException("Не найден пользователь с телефоном: " + phone);
        }
    }
}

