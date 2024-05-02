package com.app.cs151synter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ui/launch-chatbot-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Launch!");
        stage.setScene(scene);
        stage.show();


    }
}
