package com.example.hala;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MessageController {

    public String userName = "";
    private String friendName = "";

    @FXML
    public Label friendNameL;

    @FXML
    private ScrollPane commentSP;

    @FXML
    public VBox messageVB;

    @FXML
    private TextField addMessageTF;


    @FXML
    protected void onSendMessageBClick() {

        friendName =  friendNameL.getText();
        FileMessage newMessage = new FileMessage(userName,friendNameL.getText(),addMessageTF.getText());
        DataManager.addNewMessage(newMessage);
        DataManager.loadMessagesFile();
        messageVB.getChildren().clear();
        // Update the Messages VBOX
        for (int i = 0; i < DataManager.fileMessages.size(); i++) {
          FileMessage loopFileMessage = DataManager.fileMessages.get(i);

            if (  (loopFileMessage.getUserName().equals(userName) && loopFileMessage.getFriendName().equals(friendName))
                    || (loopFileMessage.getUserName().equals(friendName) && loopFileMessage.getFriendName().equals(userName))
            )
           {

              // Draw the Messages VBOX
              Label messageNewLineL = new Label("                   ");
              Label messageUserNameL = new Label(loopFileMessage.getFriendName());
              Label messageMessageL = new Label(loopFileMessage.getMessage());
              //Label messageFriendNameL = new Label(loopFileMessage.getFriendName());
              //Label messageCreationDateL = new Label(loopFileMessage.getCreationDate());
              //messageController.messageVB.getChildren().addFirst(messageFriendNameL);
              //messageController.messageVB.getChildren().addFirst(messageCreationDateL);
              if ((loopFileMessage.getUserName().equals(userName))) {
                  messageMessageL.setTextFill(Color.BLACK);
                  //messageController.messageVB.getChildren().addFirst(messageUserNameL);
                  messageVB.getChildren().addLast(messageMessageL);
                  messageVB.getChildren().addLast(messageNewLineL);
              } else {
                  messageMessageL.setTextFill(Color.MAROON);
                  //messageVB.getChildren().addLast(messageUserNameL);
                  messageVB.getChildren().addLast(messageMessageL);
                  messageVB.getChildren().addLast(messageNewLineL);
              }
          }
        }
    }

    @FXML
    protected void onMessageWindowClick() {

        friendName =  friendNameL.getText();
        DataManager.loadMessagesFile();
        messageVB.getChildren().clear();
        // Update the Messages VBOX
        for (int i = 0; i < DataManager.fileMessages.size(); i++) {
            FileMessage loopFileMessage = DataManager.fileMessages.get(i);

            if (  (loopFileMessage.getUserName().equals(userName) && loopFileMessage.getFriendName().equals(friendName))
                    || (loopFileMessage.getUserName().equals(friendName) && loopFileMessage.getFriendName().equals(userName))
            )
            {

                // Draw the Messages VBOX
                Label messageNewLineL = new Label("                   ");
                Label messageUserNameL = new Label(loopFileMessage.getFriendName());
                Label messageMessageL = new Label(loopFileMessage.getMessage());
                //Label messageFriendNameL = new Label(loopFileMessage.getFriendName());
                //Label messageCreationDateL = new Label(loopFileMessage.getCreationDate());
                //messageController.messageVB.getChildren().addFirst(messageFriendNameL);
                //messageController.messageVB.getChildren().addFirst(messageCreationDateL);
                if ((loopFileMessage.getUserName().equals(userName))) {
                    messageMessageL.setTextFill(Color.BLACK);
                    //messageController.messageVB.getChildren().addFirst(messageUserNameL);
                    messageVB.getChildren().addLast(messageMessageL);
                    messageVB.getChildren().addLast(messageNewLineL);
                } else {
                    messageMessageL.setTextFill(Color.MAROON);
                    //messageVB.getChildren().addLast(messageUserNameL);
                    messageVB.getChildren().addLast(messageMessageL);
                    messageVB.getChildren().addLast(messageNewLineL);
                }
            }
        }
    }

}
