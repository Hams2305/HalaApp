package com.example.hala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HalaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Load Data from the files
        //System.out.println(DataManager.fileUsers.size());
        DataManager.loadUsersFile();
        DataManager.loadFriendsFile();
        DataManager.loadPostsFile();
        DataManager.loadMessagesFile();
        DataManager.loadCommentsFile();
        DataManager.loadPostLikes();

        FXMLLoader fxmlLoader = new FXMLLoader(HalaApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hala Social Media Platform!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}