package com.app.cs151synter.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ClassInfoController {
    @FXML Button syllabiGenerateButton;
    @FXML private VBox syllabiInfoContainer;
    @FXML private Button homeButton;
    @FXML
    void goHomeScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        Scene scene = homeButton.getScene();
        //scene.setRoot(root);
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
    }

    public void generateSyllabi(ActionEvent actionEvent) {
        // the idea is that it would loop through the syllabi folder and take the info and place it into a label
        syllabiInfoContainer.getChildren().removeAll();

        int i = 0;
        while (i < 2) {
            Label classInfo = new Label("placeholder");
            syllabiInfoContainer.getChildren().add(classInfo);
            i++;
        }
    }
}