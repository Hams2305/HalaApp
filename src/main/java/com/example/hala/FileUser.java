package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUser {

    private int id;
    private String username;
    private String password;
    private String email;
    private String gender;
    private String bio;
    private LocalDateTime creationDateObj;
    private String creationDate;

    public FileUser(int id,String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = "";
        this.gender = "";
        this.bio = "";
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    }

    public FileUser () {
        this.username = "";
        this.password = "";
        this.email = "";
        this.gender = "";
        this.bio = "";
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    };

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }
    public String getBio() {
        return bio;
    }

    public void setID(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
}
