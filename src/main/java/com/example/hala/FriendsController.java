package com.example.hala;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendsController {

    private int userID=0;
    private String userName="";
    private String friendName = "";
    Font font;
    Font focusFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 14);
    Font normalFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);

    @FXML
    private Label friendsRL;

    @FXML
    public Label usersL;

    @FXML
    private Label friendsL;

    @FXML
    public VBox friendsVB;

    @FXML
    public Label messageL;

    @FXML
    protected void onFriendsLabelClick() {

       // Refresh File Friends.
       DataManager.loadFriendsFile();

       friendsVB.getChildren().clear();
       friendsL.setFont(focusFont);
       friendsL.setTextFill(Color.MAROON);
       friendsRL.setFont(normalFont);
       friendsRL.setTextFill(Color.BLACK);
       usersL.setFont(normalFont);
       usersL.setTextFill(Color.BLACK);

        // Fill the Friends List
        for (int i = 0; i < DataManager.fileFriends.size(); i++) {
            FileFriend loopFileFriend = DataManager.fileFriends.get(i);

            // Check the Friend Request Status 1 means still pending
            if (loopFileFriend.getStatus().equals("F") ) {
               if ( loopFileFriend.getUserName().equals(userName) || loopFileFriend.getFriendName().equals(userName) ) {

               if (loopFileFriend.getUserName().equals(userName))
                   friendName = loopFileFriend.getFriendName();
               else
                   friendName = loopFileFriend.getUserName();

               Label newLineL = new Label("                   ");
               Label  friendNameL = new Label(friendName);
               String msgFriendName = friendName;
               font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
               friendNameL.setFont(font);
               friendNameL.setTextFill(Color.BLACK);
               Label userNewLineL = new Label("                   ");
               font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
               userNewLineL.setFont(font);

               Button  messageB = new Button("Message");
               messageB.setPrefSize(70,25);
               messageB.setOnAction(event ->
               {
                 try {
                 // Open the New Messages Window
                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("message.fxml"));
                 Parent root = (Parent) fxmlLoader.load();
                 MessageController messageController = (MessageController) fxmlLoader.getController();
                 messageController.userName = userName;
                 messageController.friendNameL.setText(msgFriendName);

                 for (int k = 0; k < DataManager.fileMessages.size(); k++) {
                     FileMessage loopFileMessage = DataManager.fileMessages.get(k);
                     if (  (loopFileMessage.getUserName().equals(userName) && loopFileMessage.getFriendName().equals(msgFriendName))
                           || (loopFileMessage.getUserName().equals(msgFriendName) && loopFileMessage.getFriendName().equals(userName))
                     )
                     {
                         // Draw the Messages VBOX
                         Label messageNewLineL = new Label("                   ");
                         Label messageUserNameL = new Label(userName);
                         Label messageMessageL = new Label(loopFileMessage.getMessage());
                         //Label messageFriendNameL = new Label(loopFileMessage.getFriendName());
                         //Label messageCreationDateL = new Label(loopFileMessage.getCreationDate());
                         //messageController.messageVB.getChildren().addFirst(messageFriendNameL);
                         //messageController.messageVB.getChildren().addFirst(messageCreationDateL);
                         if ((loopFileMessage.getUserName().equals(userName))) {
                             messageMessageL.setTextFill(Color.BLACK);
                             //messageController.messageVB.getChildren().addFirst(messageUserNameL);
                             messageController.messageVB.getChildren().addLast(messageMessageL);
                             messageController.messageVB.getChildren().addLast(messageNewLineL);
                         } else {
                             messageMessageL.setTextFill(Color.MAROON);
                             //messageController.messageVB.getChildren().addLast(messageUserNameL);
                             messageController.messageVB.getChildren().addLast(messageMessageL);
                             messageController.messageVB.getChildren().addLast(messageNewLineL);
                         }
                     }
                 }

           Scene scene = new Scene(root);
           Stage stageHome = new Stage();
           stageHome.setTitle( userName + "'s Chatting Room.");
           stageHome.setScene(scene);
           stageHome.show();
           }  catch (IOException ex) {
              ex.printStackTrace();
           }

           });

               Button unfriendB = new Button("Unfriend");
               unfriendB.setPrefSize(70,25);
               unfriendB.setOnAction(event ->
               {
                   DataManager.updateFriendStatus(String.valueOf(loopFileFriend.getId()), "UF");
                   messageL.setText("Unfriend Request is confirmed.");
                   messageB.setDisable(true);
                   unfriendB.setDisable(true);
               });
               HBox hBoxFR = new HBox();
               hBoxFR.getChildren().add(messageB);
               hBoxFR.getChildren().add(unfriendB);


               friendsVB.getChildren().addFirst(newLineL);
               friendsVB.getChildren().addFirst(hBoxFR);
               friendsVB.getChildren().addFirst(userNewLineL);
               friendsVB.getChildren().addFirst(friendNameL);

               }
            }
        }
    }

    @FXML
    protected void onFriendRequestsLabelClick() {

        // Refresh File Friends.
        DataManager.loadFriendsFile();

        friendsVB.getChildren().clear();
        friendsRL.setFont(focusFont);
        friendsRL.setTextFill(Color.MAROON);
        friendsL.setFont(normalFont);
        friendsL.setTextFill(Color.BLACK);
        usersL.setFont(normalFont);
        usersL.setTextFill(Color.BLACK);

        // Fill the Friend Requests List
        for (int i = 0; i < DataManager.fileFriends.size(); i++) {
            FileFriend loopFileFriend = DataManager.fileFriends.get(i);
            // Check the Friend Request Status 0 means still pending
            if (loopFileFriend.getStatus().equals("FR") ) {
                if (loopFileFriend.getFriendName().equals(userName) ) {

                    Label newLineL = new Label("                   ");
                    friendsVB.getChildren().addFirst(newLineL);
                    Button  confirmFRB = new Button("Confirm");
                    confirmFRB.setPrefSize(70,25);
                    confirmFRB.setOnAction(event ->
                    {
                        DataManager.updateFriendStatus(String.valueOf(loopFileFriend.getId()), "F");
                        messageL.setText(" Friend Request is confirmed.");
                        confirmFRB.setDisable(true);
                    });

                    Button  rejectFRB = new Button("Reject");
                    rejectFRB.setPrefSize(70,25);
                    rejectFRB.setOnAction(event ->
                    {
                        DataManager.updateFriendStatus(String.valueOf(loopFileFriend.getId()), "UF");
                        messageL.setText(" Friend Request is rejected.");
                        rejectFRB.setDisable(true);
                    });
                    HBox hBoxFR = new HBox();
                    hBoxFR.getChildren().add(confirmFRB);
                    hBoxFR.getChildren().add(rejectFRB);
                    Label  friendNameL = new Label(loopFileFriend.getUserName());
                    font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
                    friendNameL.setFont(font);
                    friendNameL.setTextFill(Color.BLACK);
                    Label userNewLineL = new Label("                   ");
                    font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
                    userNewLineL.setFont(font);
                    friendsVB.getChildren().addFirst(hBoxFR);
                    friendsVB.getChildren().addFirst(userNewLineL);
                    friendsVB.getChildren().addFirst(friendNameL);

                }
            }
        }
    }

    @FXML
    protected void onUsersLabelClick() {

        // Refresh File Friends.
        DataManager.loadFriendsFile();

        friendsVB.getChildren().clear();
        usersL.setFont(focusFont);
        usersL.setTextFill(Color.MAROON);
        friendsRL.setFont(normalFont);
        friendsRL.setTextFill(Color.BLACK);
        friendsL.setFont(normalFont);
        friendsL.setTextFill(Color.BLACK);

        // Fill the Users List
        for (int i = 0; i < DataManager.fileUsers.size(); i++) {
            FileUser loopFileUser = DataManager.fileUsers.get(i);
            boolean isFriend = DataManager.isFriend(userName,loopFileUser.getUsername());
            if ( !isFriend && !loopFileUser.getUsername().equals(userName) ) {

                Label newLineL = new Label("                   ");
                friendsVB.getChildren().addFirst(newLineL);
                Button addFriendB = new Button("Add Friend");
                addFriendB.setPrefSize(80, 25);
                addFriendB.setOnAction(event ->
                {
                    // Add New Friend Request in the Friends File
                    FileFriend newFileFriend = new FileFriend(userName, loopFileUser.getUsername(), "FR");
                    String response = DataManager.addNewFriend(newFileFriend);
                    DataManager.loadFriendsFile();
                    messageL.setText(" New Friend Request is sent to " + loopFileUser.getUsername());
                    addFriendB.setDisable(true);
                });

                friendsVB.getChildren().addFirst(addFriendB);
                Label userNameL = new Label(loopFileUser.getUsername());
                font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
                userNameL.setFont(font);
                userNameL.setTextFill(Color.BLACK);
                Label userNewLineL = new Label("                   ");
                font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
                userNewLineL.setFont(font);
                friendsVB.getChildren().addFirst(userNewLineL);
                friendsVB.getChildren().addFirst(userNameL);
            }
        }

    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
