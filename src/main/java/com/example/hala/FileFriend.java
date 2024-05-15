package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileFriend {

    private int id;
    private String userName;
    private String friendName;
    private String status = "UF";
    private LocalDateTime creationDateObj;
    private String creationDate;

    public FileFriend(String userName,String friendName,String status) {
        this.userName = userName;
        this.friendName = friendName;
        this.status = status;
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    }

    public FileFriend () {
        this.userName = "NA";
        this.friendName = "NA";
        this.status = "UF";
        this.creationDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
        this.creationDate = this.creationDateObj.format(myFormatObj);
        myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
        this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
    };

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFriendName() {
        return this.friendName;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getStatus() {
        return this.status;
    }
    public int getId() {
            return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
    }
    public String getCreationDate() {
        return this.creationDate;
    }
}
