package com.example.Messager.controller;

import com.example.Messager.entity.Chat;
import com.example.Messager.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<?> createChat(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        try {
            System.out.println("Creating chat between " + user1Id + " and " + user2Id);
            Chat chat = chatService.createChat(user1Id, user2Id);
            System.out.println("Chat created with ID: " + chat.getId());
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            System.out.println("Error creating chat: " + e.getMessage());
            return ResponseEntity.badRequest().body("Ошибка создания чата: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserChats(@PathVariable Long userId) {
        try {
            System.out.println("Loading chats for user: " + userId);
            List<ChatDTO> chats = chatService.getUserChats(userId);
            System.out.println("Found " + chats.size() + " chats");
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            System.out.println("Error loading chats: " + e.getMessage());
            return ResponseEntity.badRequest().body("Ошибка загрузки чатов: " + e.getMessage());
        }
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable Long chatId) {
        try {
            chatService.deleteChat(chatId);
            return ResponseEntity.ok("Чат удален");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка удаления чата: " + e.getMessage());
        }
    }
}