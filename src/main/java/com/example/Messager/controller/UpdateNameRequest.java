package com.example.Messager.controller;

public class UpdateNameRequest {
    private String phone;
    private String newName;

    public UpdateNameRequest(){};
    public UpdateNameRequest(String phone, String newName) {
        this.phone = phone;
        this.newName = newName;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getNewName() { return newName; }
    public void setNewName(String newName) { this.newName = newName; }
}
