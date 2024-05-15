package com.example.hala;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class LoginController {

    private String username;
    private String password;
    private int sessionUserID = 0;
    private HomeController homeController;
    private Font font;
    Font focusFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 14);
    Font normalFont = Font.font("System Bold", FontWeight.EXTRA_BOLD, 12);

    @FXML
    private Label messageL;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private Button loginB;
    @FXML
    private Button registerB;

    @FXML
    protected void onLoginButtonClick() {
        username = usernameTF.getText();
        password = passwordTF.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageL.setText("Username and password are required");
        } else {

           try {
               String response = DataManager.validateUser(username, password);
               messageL.setText(response);
               if (response.equals("Success")) {
                    // get the Session User ID
                    //DataManager.fileUsers.ge
                    // Open the Home Window
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    homeController = (HomeController) fxmlLoader.getController();
                    homeController.userNameL.setText(username);
                    homeController.sessionUserName = username;
                   // Update the Home Screen Post List
                    updatePostsList();
                    Scene scene = new Scene(root);
                    Stage stageHome = new Stage();
                    stageHome.setTitle("Hala Social Media Platform!");
                    stageHome.setScene(scene);
                    stageHome.show();

                    // Close the Login Window
                    Stage loginStage = (Stage)usernameTF.getScene().getWindow();
                    loginStage.close();
                }

            } catch (IOException ex) {
                messageL.setText("Error connecting to server");
                ex.printStackTrace();
            }
        } // end of the validation else.
    }  // End of onClick Method

    @FXML
    protected void onRegisterButtonClick() {
        username = usernameTF.getText();
        password = passwordTF.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageL.setText("Username and password are required");
        } else {

        try {

            String response = DataManager.addNewUser(username, password);
            messageL.setText(response);

            if (response.equals("Success")) {

                // Open the Home Window
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                homeController = (HomeController) fxmlLoader.getController();
                homeController.sessionUserName = username;
                homeController.userNameL.setText(homeController.sessionUserName);
                // Update the Home Screen Post List
                updatePostsList();
                Scene scene = new Scene(root);
                Stage stageHome = new Stage();
                stageHome.setTitle("Hala Social Media Platform!");
                stageHome.setScene(scene);
                stageHome.show();

                // Close the Login Window
                Stage loginStage = (Stage)usernameTF.getScene().getWindow();
                loginStage.close();

            }

        } catch (IOException ex) {
            messageL.setText("Error connecting to server");
            ex.printStackTrace();
        }
        } // end of the validation else.
        //messageL.setText("Welcome to JavaFX Application!");
    }  // End of onRegister Click Method

    protected void updatePostsList() {
        username = usernameTF.getText();
        password = passwordTF.getText();

        // Change the User Profile Image
        sessionUserID = DataManager.getUserID(username);
        Image newProfileImage = new Image("file:///C://data//images//"+sessionUserID+".jpg");
        if (newProfileImage.isError())
            newProfileImage = new Image("file:///C://data//images//0.jpg");
        homeController.imageView.setImage(newProfileImage);
        DataManager.loadPostsFile();
        homeController.postVB.getChildren().clear();
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
                if ( !DataManager.isPostLikedBefore(loopFilePost.getID(),username) ) {
                    DataManager.updatePostLikesCounts(Integer.toString(loopFilePost.getID()), Integer.toString(loopFilePost.getLikesCount() + 1));
                    loopFilePost.LikesPlus();
                    postLikes.setText("   " + String.valueOf(loopFilePost.getLikesCount()) + " likes");
                    // Add new Likes record in the Likes File
                    FileLike fileLike = new FileLike(loopFilePost.getID(), username);
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
                    commentsController.sessionUserName = username;
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
                    stageHome.setTitle( username + " - Comments");
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
            homeController.postVB.getChildren().addFirst(postNewLine);
            homeController.postVB.getChildren().addFirst(postHB);
            homeController.postVB.getChildren().addFirst(postContentL);
            homeController.postVB.getChildren().addFirst(autherNewLine);
            homeController.postVB.getChildren().addFirst(postCreationDateL);
            homeController.postVB.getChildren().addFirst(postAuthorL);
        }
    }

}
