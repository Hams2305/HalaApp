package com.example.hala;

import javafx.fxml.FXML;
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

public class CommentsController {

    public String sessionUserName ="";
    private String userName="";
    private FilePost currentPost;

    @FXML
    public Label postUserNameL;

    @FXML
    public Label postDateL;

    @FXML
    public Label postL;

    @FXML
    private ScrollPane commentSP;

    @FXML
    public VBox commentVB;

    @FXML
    public TextField addCommentTF;

    @FXML
    protected void onAddCommentBClick() {

        FileComment newComment = new FileComment(currentPost.getID(),currentPost.getAuthor(),addCommentTF.getText(),sessionUserName);
        DataManager.addNewComment(newComment, currentPost.getCommentsCount()+1 );
        DataManager.loadCommentsFile();
        commentVB.getChildren().clear();
        // Update the Comments VBOX
        for (int i = 0; i < DataManager.fileComments.size(); i++) {
            FileComment loopFileComment = DataManager.fileComments.get(i);
            if (loopFileComment.getPostID() == currentPost.getID()) {
                // Draw the Comments VBOX
                Label commentNewLineL = new Label("                   ");
                Label mcommentUserNameL = new Label(loopFileComment.getCreatedBy());
                Label CommentsTextL = new Label(loopFileComment.getComment());
                //messageMessageL.setTextFill(Color.MAROON);
                commentVB.getChildren().addLast(commentNewLineL);
                commentVB.getChildren().addLast(mcommentUserNameL);
                commentVB.getChildren().addLast(CommentsTextL);
            }
        }
    }

    public void setPost(FilePost post) {
        this.currentPost = post;
    }

    @FXML
    protected void onCommentsWindowClick() {

        DataManager.loadCommentsFile();
        commentVB.getChildren().clear();
        // Update the Comments VBOX
        for (int i = 0; i < DataManager.fileComments.size(); i++) {
            FileComment loopFileComment = DataManager.fileComments.get(i);
            if (loopFileComment.getPostID() == currentPost.getID())
            {
            // Draw the Comments VBOX
            Label commentNewLineL = new Label("                   ");
            Label mcommentUserNameL = new Label(loopFileComment.getCreatedBy());
            Label CommentsTextL = new Label(loopFileComment.getComment());
            //messageMessageL.setTextFill(Color.MAROON);
            commentVB.getChildren().addLast(commentNewLineL);
            commentVB.getChildren().addLast(mcommentUserNameL);
            commentVB.getChildren().addLast(CommentsTextL);
            }
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
