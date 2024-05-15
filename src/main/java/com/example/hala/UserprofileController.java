package com.example.hala;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class UserprofileController {

    public String sessionUserName ="";
    private String userName="";
    private FilePost currentPost;

    @FXML
    public Label userNameDisplayL;

    @FXML
    private Label messageL;

    @FXML
    public TextField emailTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    public TextField genderTF;

    @FXML
    public TextArea bioTA;

    @FXML
    private Button submitB;

    @FXML
    protected void onSubmitButtonClick() {

        if (passwordTF.getText().isEmpty()) {
            messageL.setText("Password is required");
        } else {
            FileUser updateFileUser = new FileUser();
            updateFileUser.setUsername(sessionUserName);
            updateFileUser.setID(DataManager.getUserID(sessionUserName));
            updateFileUser.setPassword(passwordTF.getText());
            updateFileUser.setEmail(emailTF.getText());
            updateFileUser.setGender(genderTF.getText());
            updateFileUser.setBio(bioTA.getText());
            String response =DataManager.updateFileUser(updateFileUser);
            if (response.equals("Success"))
                messageL.setText("User Profile updated successfully.");
            else
                messageL.setText("Not able to update the User Profile.");
            DataManager.loadUsersFile();
        }
     }

}
