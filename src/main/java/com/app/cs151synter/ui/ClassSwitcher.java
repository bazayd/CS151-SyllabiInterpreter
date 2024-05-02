package com.app.cs151synter.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassSwitcher {

    private static Stage mainStage;

    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    static Stage getMainStage() {
        return mainStage;
    }

    public static void switchScene(Node n, String filename) throws NullPointerException, IOException {
        Parent root = FXMLLoader.load(n.getClass().getResource(filename));
        mainStage.setScene(new Scene(root));
    }

}
