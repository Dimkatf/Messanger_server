package com.example.Messager.controller;

public class UpdateMessageDTO {
    private Long id;
    private String newText;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNewText() { return newText; }
    public void setNewText(String newText) { this.newText = newText; }
}
