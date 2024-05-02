package com.app.cs151synter.ui;

import com.app.cs151synter.dataContainers.Assignment;
import com.app.cs151synter.dataContainers.DatedSyllabusEntity;
import com.app.cs151synter.dataManipulation.IntentResponse;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Random;
import java.util.GregorianCalendar;

public class MainController extends Application {
    // to reference with other
    private static Scene mainScreen;
    private static Stage mainStage;
    private static FileChooser fileChooser;

    @FXML Button openSyllabiFolderButton;
    @FXML private Button uploadSyllabusButton;
    @FXML private MenuItem upLoadFromFileSystemButton;
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

        // scene creation
        createMainScene(root);
        mainStage = stage;
        mainStage.setTitle("Syllabi Interpreter (CS 151)");
        mainStage.setScene(mainScreen);
        mainStage.show();
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
        //scene.setRoot(root);
        Window window = scene.getWindow();
        Stage stage = (Stage) window;


        stage.setScene(new Scene(root));
    }
    private List<DatedSyllabusEntity> getTest() {
        List<DatedSyllabusEntity> datedSyllabusEntities = new ArrayList<>();
        int year = ZonedDateTime.now().getYear();
        int month = ZonedDateTime.now().getMonthValue();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            ZonedDateTime time = ZonedDateTime.of(year, random.nextInt(11)+1, random.nextInt(27)+1, 16,0,0,0,ZonedDateTime.now().getZone());
            Calendar calendar = GregorianCalendar.from(time);
            datedSyllabusEntities.add(new Assignment("fart", calendar));
        }
        return datedSyllabusEntities;
    }
    @FXML
    void toCalendar(javafx.event.ActionEvent actionEvent) throws Exception {

        FXMLLoader b = new FXMLLoader(getClass().getResource(("Calendar.fxml")));
        //AnchorPane calendar = (AnchorPane) b.load();
        Parent a = b.load();
        CalendarViewController calendarViewController = b.getController();
        calendarViewController.setDatedSyllabusEntities(getTest());
        //Button back = calendarViewController.getHomeButton();

        Scene scene = getMainScreen();

        Window window = scene.getWindow();

        mainStage.setScene(new Scene(a));

    }

    @FXML
    void toToDoList(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader b = new FXMLLoader(getClass().getResource("ListView.fxml"));
        AnchorPane calendar = (AnchorPane) b.load();
        Parent a = calendar;
        ListViewController listViewController = b.getController();
        listViewController.setDatedSyllabusEntities(getTest());
        Scene scene = createMainScene(a);
        //scene.setRoot(a);
        Window window = scene.getWindow();
        Stage stage = (Stage) window;
        mainStage.setScene(new Scene(a));
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

    }


}
