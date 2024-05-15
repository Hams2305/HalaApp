
package com.example.hala;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMessage {


        private int id;
        private String userName;
        private String friendName;
        private String message;
        private LocalDateTime creationDateObj;
        private String creationDate;

        public FileMessage(String userName,String friendName,String message) {
            this.userName = userName;
            this.friendName = friendName;
            this.message = message;
            this.creationDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yy - HH:mm:ss");
            this.creationDate = this.creationDateObj.format(myFormatObj);
            myFormatObj = DateTimeFormatter.ofPattern("MMddHHmmss");
            this.id = Integer.parseInt(this.creationDateObj.format(myFormatObj));
        }

        public FileMessage () {
            this.userName = "NA";
            this.friendName = "NA";
            this.message = "NA";
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
        public String getFriendName() {
            return this.friendName;
        }
        public String getUserName() {
            return this.userName;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getCreationDate() {
            return creationDate;
    }
}
