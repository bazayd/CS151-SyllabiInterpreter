package com.app.cs151synter.ui;

import com.app.cs151synter.Main;
import com.app.cs151synter.dataContainers.Syllabus;
import com.app.cs151synter.dataManipulation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ChatbotViewController {

    @FXML
    public TextField textInput;

    @FXML
    private VBox dialogHolder;

    @FXML
    private Button homeButton;



    @FXML
    public void submitResponse(ActionEvent event) throws IOException {
        String response = "You: " + textInput.getText();

        Label chatBubble = new Label(response);
        dialogHolder.getChildren().addAll(chatBubble);

        chatBubble = new Label(botResponse());
        dialogHolder.getChildren().addAll(chatBubble);

        textInput.clear();
    }

    private String botResponse() throws IOException {
        MainController mainController = new MainController();
        System.out.println(mainController.nlpResponse(textInput.getText()));
        return mainController.nlpResponse(textInput.getText());
    }


    @FXML
    private void headHome() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        Scene scene = homeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
    }

}
