package com.example.Messager.service;

import com.example.Messager.entity.Chat;
import com.example.Messager.entity.User;
import com.example.Messager.repository.ChatRepository;
import com.example.Messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.Messager.controller.ChatDTO;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public Chat createChat(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new RuntimeException("User1 not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new RuntimeException("User2 not found"));

        Chat existingChat = chatRepository.findChatBetweenUsers(user1Id, user2Id);
        if (existingChat != null) {
            return existingChat;
        }

        Chat chat = new Chat(user1, user2);
        return chatRepository.save(chat);
    }

    public List<ChatDTO> getUserChats(Long userId) {
        List<Chat> chats = chatRepository.findUserChats(userId);

        return chats.stream().map(chat -> {
            String user1Name = chat.getUser1().getUserName();
            String user2Name = chat.getUser2().getUserName();

            return new ChatDTO(
                    chat.getId(),
                    chat.getUser1().getId(),
                    chat.getUser2().getId(),
                    user1Name,
                    user2Name,
                    chat.getLastMessage(),
                    chat.getLastMessageTime(),
                    chat.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}