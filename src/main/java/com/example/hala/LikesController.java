package com.example.hala;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LikesController {

    @FXML
    public Label userNameL;

    @FXML
    public Label postDateL;

    @FXML
    public Label postL;

    @FXML
    private ScrollPane likesSP;

    @FXML
    public VBox likesVB;

    @FXML
    protected void onBackClick() {

        // Close the Login Window
        Stage LikesStage = (Stage)userNameL.getScene().getWindow();
        LikesStage.close();

    }

}
