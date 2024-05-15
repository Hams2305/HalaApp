package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FilePost {

    private int id;
    private int likesCount;
    private int commentsCount;
    private String author;
    private String content;
    private LocalDateTime creationDateObj;
    private String creationDate;

    public FilePost(String author, String content) {
        this.author = author;
        this.content = content;
        this.likesCount = 0;
        this.commentsCount = 0;
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    }

    public FilePost () {
        this.id = id;
        this.author = "";
        this.content = "";
        this.likesCount = 0;
        this.commentsCount = 0;
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    };

    public int getID() {
        return id;
    }
    public String getCreationDate() {
        return creationDate;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author ) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void LikesPlus() {
        likesCount++;
    }

    public void CommentsPlus() {
        commentsCount++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
            this.content = content;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
