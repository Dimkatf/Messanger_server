package com.example.Messager.controller;


import java.time.LocalDateTime;

public class ChatDTO {
    private Long id;
    private Long user1Id;
    private Long user2Id;
    private String user1Name;
    private String user2Name;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private LocalDateTime createdAt;

    // Конструкторы, геттеры, сеттеры
    public ChatDTO() {}

    public ChatDTO(Long id, Long user1Id, Long user2Id, String user1Name, String user2Name,
                   String lastMessage, LocalDateTime lastMessageTime, LocalDateTime createdAt) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.user1Name = user1Name;
        this.user2Name = user2Name;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUser1Id() { return user1Id; }
    public void setUser1Id(Long user1Id) { this.user1Id = user1Id; }

    public Long getUser2Id() { return user2Id; }
    public void setUser2Id(Long user2Id) { this.user2Id = user2Id; }

    public String getUser1Name() { return user1Name; }
    public void setUser1Name(String user1Name) { this.user1Name = user1Name; }

    public String getUser2Name() { return user2Name; }
    public void setUser2Name(String user2Name) { this.user2Name = user2Name; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public LocalDateTime getLastMessageTime() { return lastMessageTime; }
    public void setLastMessageTime(LocalDateTime lastMessageTime) { this.lastMessageTime = lastMessageTime; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
