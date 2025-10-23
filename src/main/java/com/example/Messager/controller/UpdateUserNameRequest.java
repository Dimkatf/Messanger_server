package com.example.Messager.controller;

public class UpdateUserNameRequest {
    private String phone;
    private String newUserName;

    public UpdateUserNameRequest(){}
    public UpdateUserNameRequest(String phone, String newUserName){
        this.phone = phone;
        this.newUserName = newUserName;
    }
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getNewUserName() {return newUserName;}
    public void setNewUserName(String userName) {this.newUserName = userName;}
}
