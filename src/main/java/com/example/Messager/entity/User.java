package com.example.Messager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String password;
    private String photoFilename;

    @Column(nullable = true, name = "user_name")
    private String userName;

    public User() {}

    public User(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getPhotoFilename() {return photoFilename;}
    public void setPhotoFilename(String photoFilename) {this.photoFilename = photoFilename;}
    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", photoFilename='" + photoFilename + '\'' +
                ", userName= '" + userName + '\'' +
                '}';
    }
}