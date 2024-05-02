package com.app.cs151synter.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;


public class MainController extends Application {
    // to reference with other
    private static Scene mainScreen;
    private static FileChooser fileChooser;
    private static Stage mainStage;

    @FXML Button openSyllabiFolderButton;
    @FXML private Button uploadSyllabusButton;
    @FXML private Button classInfoButton;
    @FXML private Button chatbotButton;
    @FXML Button toDoListButton;
    @FXML Button calendarButton;

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("syllabusmain.fxml"));
        Parent root = fxmlLoader.load();

        // file manager set up
        getFileChooser().setTitle("Upload Syllabus");
        getFileChooser().setInitialDirectory(new File("C:"));

        // scene creation
        createMainScene(root);
        mainStage = stage;
        stage.setTitle("Syllabi Interpreter (CS 151)");
        stage.setScene(mainScreen);
        stage.show();
    }

    public static Scene createMainScene(Parent p) {
        if (mainScreen == null) {
            mainScreen = new Scene(p);
        }
        return mainScreen;
    }

    public static Scene getMainScreen() {
        return mainScreen;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
        }
        return fileChooser;
    }

    public void switchScene(Node n, String filename) throws Exception {
        Parent root = new AnchorPane();
        try {
            root = FXMLLoader.load(getClass().getResource(filename));
        }
        catch (NullPointerException e) {
            e.getStackTrace();
        }
        getMainStage().setScene(new Scene(root));
    }

    @FXML
    void toCalendar(javafx.event.ActionEvent actionEvent) {

    }

    @FXML
    void toToDoList(javafx.event.ActionEvent actionEvent) {

    }

    @FXML
    void goToClassInfo(javafx.event.ActionEvent actionEvent) throws Exception {
        switchScene(classInfoButton, "syllabusclassinfo.fmxl");
    }

    @FXML
    void toChatbot(javafx.event.ActionEvent actionEvent) throws Exception {
        switchScene(chatbotButton, "syllabuschatbox.fxml");
    }

    @FXML
    void syllabusPicker() throws IOException {
        File syllabus = getFileChooser().showOpenDialog(getMainScreen().getWindow());
        if (syllabus == null) {
            return; // please select something lol (also maybe tell the user that through a label or something
        }
        Path temp = Files.move(Paths.get(syllabus.getPath()), Paths.get("syllabus"));

        if (temp != null) {
            // congrats, it worked, tell the user that
        }
        else {
            // well shit
        }
    }

    @FXML
    void openSyllabiFolder() throws IOException {
        // opens the folder containing syllabi (change the brackets)

    }
}
