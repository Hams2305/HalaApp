package com.example.hala;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    private Font font;
    Font focusFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 14);
    Font normalFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
    String userName = "";
    public String sessionUserName ="";

    @FXML
    private ScrollPane postSP;

    @FXML
    public VBox postVB;

    @FXML
    public Label userNameL;

    @FXML
    public ImageView imageView;

    @FXML
    private TextField addPostTF;

    @FXML
    private Button addPostB;

    @FXML
    protected void onAddPostButtonClick() {

        // Create New FilePost Instance
        userName = userNameL.getText();
        String postContent = addPostTF.getText();
        FilePost newFilePost = new FilePost(userName,postContent);
        DataManager.addNewPost(newFilePost);
        DataManager.loadPostsFile();
        postVB.getChildren().clear();
        // Update the Posts VBOX

        for (int k = 0; k < DataManager.filePosts.size(); k++) {
            FilePost loopFilePost = DataManager.filePosts.get(k);
            Label postAuthorL = new Label(loopFilePost.getAuthor());
            //postAuthorLFont = new Font("System Bold", Font.BOLD, 11);
            //postAuthorL.setFont(new Font(12));
            Label postCreationDateL = new Label(loopFilePost.getCreationDate());
            Label postContentL = new Label(loopFilePost.getContent());
            //Font postFont = new Font("System Bold",1,"14.0");
            font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
            postContentL.setFont(font);
            postContentL.setTextFill(Color.DEEPPINK);
            HBox postHB = new HBox();
            //postHB.setId("001");
            // Add the Post Likes Label
            Label postLikes = new Label();
            postLikes.setId(String.valueOf(loopFilePost.getID()) + "_lC");
            postLikes.setText("   " + String.valueOf(loopFilePost.getLikesCount()) + " likes");
            postLikes.setOnMouseClicked(event ->
            {
                try {
                    // Open the Likes List Window
                    DataManager.loadPostLikes();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("likes.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    LikesController likesController = (LikesController) fxmlLoader.getController();
                    likesController.userNameL.setText(loopFilePost.getAuthor());
                    likesController.postDateL.setText(loopFilePost.getCreationDate());
                    likesController.postL.setText(loopFilePost.getContent());
                    // Populate the Like VBox
                    List<FileLike> postLikesList = DataManager.getPostLikes(loopFilePost.getID());
                    for (int i = 0; i < postLikesList.size(); i++) {
                        FileLike loopFileLike = postLikesList.get(i);
                        likesController.likesVB.getChildren().addFirst(new Label("                   "));
                        likesController.likesVB.getChildren().addFirst(new Label(loopFileLike.getCreationDate()));
                        likesController.likesVB.getChildren().addFirst(new Label(loopFileLike.getCreatedBy()));
                    }

                    Scene scene = new Scene(root);
                    Stage stageHome = new Stage();
                    stageHome.setTitle(" Hala - Likes List");
                    stageHome.setScene(scene);
                    stageHome.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            // Add New Like Label
            Label postAddNewLike = new Label();
            postAddNewLike.setId(String.valueOf(loopFilePost.getID()) + "_NL");
            postAddNewLike.setText("   Like");
            postAddNewLike.setOnMouseClicked(event ->
            {
                // Check if the User already liked this Post before.
                if ( !DataManager.isPostLikedBefore(loopFilePost.getID(),sessionUserName) ) {
                    DataManager.updatePostLikesCounts(Integer.toString(loopFilePost.getID()), Integer.toString(loopFilePost.getLikesCount() + 1));
                    loopFilePost.LikesPlus();
                    postLikes.setText("   " + String.valueOf(loopFilePost.getLikesCount()) + " likes");
                    // Add new Likes record in the Likes File
                    FileLike fileLike = new FileLike(loopFilePost.getID(), sessionUserName);
                    DataManager.addPostLike(fileLike);
                    DataManager.loadPostLikes();
                }
            });
            // Add New Comments Label //////////////////////////////////////////////////////////////////////////
            Label postComments = new Label();
            postComments.setId(String.valueOf(loopFilePost.getID()) + "_NC");
            postComments.setText("   " + String.valueOf(loopFilePost.getCommentsCount()) + " Comments");
            postComments.setOnMouseClicked(event ->
            {
                try {
                    // Open the New Comment Window
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comments.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    CommentsController commentsController = (CommentsController) fxmlLoader.getController();
                    commentsController.sessionUserName = sessionUserName;
                    commentsController.setPost(loopFilePost);
                    commentsController.postUserNameL.setText(loopFilePost.getAuthor());
                    commentsController.postDateL.setText(loopFilePost.getCreationDate());
                    commentsController.postL.setText(loopFilePost.getContent());
                    commentsController.addCommentTF.setText(userNameL.getText());

                    DataManager.loadCommentsFile();
                    commentsController.commentVB.getChildren().clear();
                    // Update the Comments VBOX
                    for (int i = 0; i < DataManager.fileComments.size(); i++) {
                        FileComment loopFileComment = DataManager.fileComments.get(i);
                        if (loopFileComment.getPostID() == loopFilePost.getID())
                        {
                            // Draw the Comments VBOX
                            Label commentNewLineL = new Label("                   ");
                            Label mcommentUserNameL = new Label(loopFileComment.getCreatedBy());
                            Label CommentsTextL = new Label(loopFileComment.getComment());
                            //messageMessageL.setTextFill(Color.MAROON);
                            commentsController.commentVB.getChildren().addLast(commentNewLineL);
                            commentsController.commentVB.getChildren().addLast(mcommentUserNameL);
                            commentsController.commentVB.getChildren().addLast(CommentsTextL);
                        }
                    }

                    Scene scene = new Scene(root);
                    Stage stageHome = new Stage();
                    stageHome.setTitle( sessionUserName +  " - Comments");
                    stageHome.setScene(scene);
                    stageHome.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            // Add all items to HBOX
            postHB.getChildren().addAll(postLikes, postAddNewLike, postComments);
            Label postNewLine = new Label();
            postNewLine.setText("                                             ");
            Label autherNewLine = new Label();
            autherNewLine.setText("                                             ");
            font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
            autherNewLine.setFont(font);

            // Add the Post Content
            postVB.getChildren().addFirst(postNewLine);
            postVB.getChildren().addFirst(postHB);
            postVB.getChildren().addFirst(postContentL);
            postVB.getChildren().addFirst(autherNewLine);
            postVB.getChildren().addFirst(postCreationDateL);
            postVB.getChildren().addFirst(postAuthorL);

            // Clean the old Post Text
            addPostTF.setText("");
            addPostTF.setPromptText("What's on your mind?");
        }
    }

    @FXML
    protected void onFriendsButtonClick() {
        try {
            // Open the New Comment Window
            String userName = userNameL.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Friends.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            // Fill the VBOX Lists
            FriendsController friendsController = (FriendsController) fxmlLoader.getController();
            friendsController.setUserID(1);
            friendsController.setUserName(userNameL.getText());
            friendsController.usersL.setFont(focusFont);
            friendsController.usersL.setTextFill(Color.MAROON);

            // Fill the Users List
            for (int i = 0; i < DataManager.fileUsers.size(); i++) {
                FileUser loopFileUser = DataManager.fileUsers.get(i);
                boolean isFriend = DataManager.isFriend(userName,loopFileUser.getUsername());
                if ( !isFriend && !loopFileUser.getUsername().equals(userName) ) {

                    Label newLineL = new Label("                   ");
                    friendsController.friendsVB.getChildren().addFirst(newLineL);
                    Button addFriendB = new Button("Add Friend");
                    addFriendB.setPrefSize(80, 25);
                    // Add Friend Action Code
                    addFriendB.setOnAction(event ->
                    {
                        // Add New Friend Request in the Friends File
                        FileFriend newFileFriend = new FileFriend(userNameL.getText(), loopFileUser.getUsername(), "FR");
                        String response = DataManager.addNewFriend(newFileFriend);
                        DataManager.loadFriendsFile();
                        friendsController.messageL.setText(" New Friend Request is sent to " + loopFileUser.getUsername());
                        addFriendB.setDisable(true);
                    });

                    friendsController.friendsVB.getChildren().addFirst(addFriendB);
                    Label userNameL = new Label(loopFileUser.getUsername());
                    font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
                    userNameL.setFont(font);
                    userNameL.setTextFill(Color.BLACK);
                    Label userNewLineL = new Label("                   ");
                    font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
                    userNewLineL.setFont(font);
                    friendsController.friendsVB.getChildren().addFirst(userNewLineL);
                    friendsController.friendsVB.getChildren().addFirst(userNameL);
                }
            }

            Scene scene = new Scene(root);
            Stage stageHome = new Stage();
            stageHome.setTitle(" "+ userNameL.getText()+ "'s Friends.");
            stageHome.setScene(scene);
            stageHome.show();
        }  catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void onLabelMouseClick(MouseEvent mouseEvent) {

        try {
            // Open the User Profile Window
            FileUser sessionUserRecord = DataManager.getUserRecord(sessionUserName);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userprofile.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            UserprofileController userprofileController = (UserprofileController) fxmlLoader.getController();
            userprofileController.userNameDisplayL.setText(sessionUserName);
            userprofileController.sessionUserName = sessionUserName;
            userprofileController.emailTF.setText(sessionUserRecord.getEmail());
            userprofileController.genderTF.setText(sessionUserRecord.getGender());
            userprofileController.bioTA.setText(sessionUserRecord.getBio());

            Scene scene = new Scene(root);
            Stage stageHome = new Stage();
            stageHome.setTitle( sessionUserName +  " - User Profile.");
            stageHome.setScene(scene);
            stageHome.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    protected void onHomeWindowClick() {
        // Refresh the Posts VBox
        userName = userNameL.getText();
        DataManager.loadPostsFile();
        postVB.getChildren().clear();
        // Update the Posts VBOX

        for (int k = 0; k < DataManager.filePosts.size(); k++) {
            FilePost loopFilePost = DataManager.filePosts.get(k);
            Label postAuthorL = new Label(loopFilePost.getAuthor());
            //postAuthorLFont = new Font("System Bold", Font.BOLD, 11);
            //postAuthorL.setFont(new Font(12));
            Label postCreationDateL = new Label(loopFilePost.getCreationDate());
            Label postContentL = new Label(loopFilePost.getContent());
            //Font postFont = new Font("System Bold",1,"14.0");
            font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);
            postContentL.setFont(font);
            postContentL.setTextFill(Color.DEEPPINK);
            HBox postHB = new HBox();
            //postHB.setId("001");
            // Add the Post Likes Label
            Label postLikes = new Label();
            postLikes.setId(String.valueOf(loopFilePost.getID()) + "_lC");
            postLikes.setText("   " + String.valueOf(loopFilePost.getLikesCount()) + " likes");
            postLikes.setOnMouseClicked(event ->
            {
                try {
                    // Open the Likes List Window
                    DataManager.loadPostLikes();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("likes.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    LikesController likesController = (LikesController) fxmlLoader.getController();
                    likesController.userNameL.setText(loopFilePost.getAuthor());
                    likesController.postDateL.setText(loopFilePost.getCreationDate());
                    likesController.postL.setText(loopFilePost.getContent());
                    // Populate the Like VBox
                    List<FileLike> postLikesList = DataManager.getPostLikes(loopFilePost.getID());
                    for (int i = 0; i < postLikesList.size(); i++) {
                        FileLike loopFileLike = postLikesList.get(i);
                        likesController.likesVB.getChildren().addFirst(new Label("                   "));
                        likesController.likesVB.getChildren().addFirst(new Label(loopFileLike.getCreationDate()));
                        likesController.likesVB.getChildren().addFirst(new Label(loopFileLike.getCreatedBy()));
                    }

                    Scene scene = new Scene(root);
                    Stage stageHome = new Stage();
                    stageHome.setTitle(" Hala - Likes List");
                    stageHome.setScene(scene);
                    stageHome.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            // Add New Like Label
            Label postAddNewLike = new Label();
            postAddNewLike.setId(String.valueOf(loopFilePost.getID()) + "_NL");
            postAddNewLike.setText("   Like");
            postAddNewLike.setOnMouseClicked(event ->
            {
                // Check if the User already liked this Post before.
                if ( !DataManager.isPostLikedBefore(loopFilePost.getID(),sessionUserName) ) {
                    DataManager.updatePostLikesCounts(Integer.toString(loopFilePost.getID()), Integer.toString(loopFilePost.getLikesCount() + 1));
                    loopFilePost.LikesPlus();
                    postLikes.setText("   " + String.valueOf(loopFilePost.getLikesCount()) + " likes");
                    // Add new Likes record in the Likes File
                    FileLike fileLike = new FileLike(loopFilePost.getID(), sessionUserName);
                    DataManager.addPostLike(fileLike);
                    DataManager.loadPostLikes();
                }
            });
            // Add New Comments Label
            Label postComments = new Label();
            postComments.setId(String.valueOf(loopFilePost.getID()) + "_NC");
            postComments.setText("   " + String.valueOf(loopFilePost.getCommentsCount()) + " Comments");
            postComments.setOnMouseClicked(event ->
            {
                try {
                    // Open the New Comment Window
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comments.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    CommentsController commentsController = (CommentsController) fxmlLoader.getController();
                    commentsController.setPost(loopFilePost);
                    commentsController.sessionUserName = sessionUserName;
                    commentsController.postUserNameL.setText(loopFilePost.getAuthor());
                    commentsController.postDateL.setText(loopFilePost.getCreationDate());
                    commentsController.postL.setText(loopFilePost.getContent());

                    DataManager.loadCommentsFile();
                    commentsController.commentVB.getChildren().clear();
                    // Update the Comments VBOX
                    for (int i = 0; i < DataManager.fileComments.size(); i++) {
                        FileComment loopFileComment = DataManager.fileComments.get(i);
                        if (loopFileComment.getPostID() == loopFilePost.getID())
                        {
                            // Draw the Comments VBOX
                            Label commentNewLineL = new Label("                   ");
                            Label mcommentUserNameL = new Label(loopFileComment.getCreatedBy());
                            Label CommentsTextL = new Label(loopFileComment.getComment());
                            //messageMessageL.setTextFill(Color.MAROON);
                            commentsController.commentVB.getChildren().addLast(commentNewLineL);
                            commentsController.commentVB.getChildren().addLast(mcommentUserNameL);
                            commentsController.commentVB.getChildren().addLast(CommentsTextL);
                        }
                    }

                    Scene scene = new Scene(root);
                    Stage stageHome = new Stage();
                    stageHome.setTitle( sessionUserName +  " - Comments");
                    stageHome.setScene(scene);
                    stageHome.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            // Add all items to HBOX
            postHB.getChildren().addAll(postLikes, postAddNewLike, postComments);
            Label postNewLine = new Label();
            postNewLine.setText("                                             ");
            Label autherNewLine = new Label();
            autherNewLine.setText("                                             ");
            font = Font.font("System Bold", FontWeight.EXTRA_BOLD, 3);
            autherNewLine.setFont(font);

            // Add the Post Content
            postVB.getChildren().addFirst(postNewLine);
            postVB.getChildren().addFirst(postHB);
            postVB.getChildren().addFirst(postContentL);
            postVB.getChildren().addFirst(autherNewLine);
            postVB.getChildren().addFirst(postCreationDateL);
            postVB.getChildren().addFirst(postAuthorL);

            // Clean the old Post Text
            addPostTF.setText("");
            addPostTF.setPromptText("What's on your mind?");
        }
    }
}