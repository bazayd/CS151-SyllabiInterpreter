package com.app.cs151synter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        logger.setLevel(Level.ALL);
        launch();
    }

    public void start(Stage stage) throws Exception {
        // TODO
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("launch-chatbot-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Launch!");
        stage.setScene(scene);
        stage.show();
    }
}
