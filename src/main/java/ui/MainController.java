package ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainController extends Application {
    // to reference with other
    private static Scene mainScreen;
    private static FileChooser fileChooser;

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


        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        // scene creation
        createMainScene(root);
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

    public static FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
        }
        return fileChooser;
    }

    public void switchScene(Node n, String filename) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(filename));

        Scene scene = n.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
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
        getFileChooser().setTitle("Upload Syllabus");
        File syllabus = getFileChooser().showOpenDialog(uploadSyllabusButton.getScene().getWindow());

        // move file
    }

    @FXML
    void openSyllabiFolder() throws IOException {
        // opens the folder containing syllabi (change the brackets)
        Desktop.getDesktop().open(new File("C:\\folder"));
    }
}
