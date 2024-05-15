package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLike {

    private int id;
    private int postID;
    private String createdBy;
    private LocalDateTime creationDateObj;
    private String creationDate;

    public FileLike(int postID,String createdBy) {
        this.postID = postID;
        this.createdBy = createdBy;
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    }

    public FileLike () {
        this.postID = 0;
        this.createdBy = "";
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    };

    public int getId() {
        return id;
    }
    public int getPostID() {
        return postID;
    }
    public String getCreatedBy() {
        return createdBy;
    }
   public String getCreationDate() {
        return creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPostID(int postID) {
        this.postID = postID;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
