package com.example.Messager.repository;

import com.example.Messager.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatIdOrderByIdAsc(String chatId);
}
