package com.example.Messager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    private String sender;
    private String text;
    @Column(name = "is_edited")
    private boolean isEdited;

    public Message(){}
    public Message(String chatId, String sender, String text){
        this.chatId = chatId;
        this.sender = sender;
        this.text = text;
        this.isEdited = false;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getChatId() {return chatId;}
    public void setChatId(String chatId) {this.chatId = chatId;}
    public String getSender() {return sender;}
    public void setSender(String sender) {this.sender = sender;}
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public boolean isEdited() { return isEdited; }
    public void setEdited(boolean edited) { isEdited = edited; }
}
