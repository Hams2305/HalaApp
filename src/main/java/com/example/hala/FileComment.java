package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileComment {

    private int id;
    private int postID;
    private String comment;
    private String createdBy;
    private String postUserName;
    private LocalDateTime creationDateObj;
    private String creationDate;

    public FileComment(int postID,String postUserName, String comment, String author) {
        this.postID = postID;
        this.postUserName = postUserName;
        this.createdBy = author;
        this.comment = comment;
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    }

    public FileComment () {
        this.postID = 0;
        this.postUserName = "";
        this.createdBy = "";
        this.comment = "";
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPostID() {
        return postID;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getPostUserName() {
        return postUserName;
    }
    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getCreationDate() {
        return creationDate;
    }
}
