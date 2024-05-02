package com.app.cs151synter.ui;

import com.app.cs151synter.dataContainers.Syllabus;
import com.app.cs151synter.dataContainers.SyllabusEntity;
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

import java.util.ArrayList;
import java.util.List;

public class ClassInfoController {
    @FXML Button syllabiGenerateButton;
    @FXML private VBox syllabiInfoContainer = new VBox();
    @FXML private Button homeButton;
    private List<SyllabusEntity> syllabusEntityList;
    @FXML
    void goHomeScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        Scene scene = homeButton.getScene();
        //scene.setRoot(root);
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
    }
    public List<SyllabusEntity> getSyllabusEntityList() {
        return syllabusEntityList;
    }
    public void setSyllabusEntityList(List<SyllabusEntity> syllabusEntityList) {
        this.syllabusEntityList = syllabusEntityList;
        for(SyllabusEntity a : syllabusEntityList) {
            String[] info = a.getInfo();
            for(String desc: info) {
                Label label = new Label();
                label.setText(desc);
                syllabiInfoContainer.getChildren().add(label);
            }
        }
    }
    public void generateSyllabi(ActionEvent actionEvent) {
        // the idea is that it would loop through the syllabi folder and take the info and place it into a label
        for(SyllabusEntity a : syllabusEntityList) {
            String[] info = a.getInfo();
            for(String desc: info) {
                Label label = new Label();
                label.setText(desc);
                syllabiInfoContainer.getChildren().add(label);
            }
        }
    }
}