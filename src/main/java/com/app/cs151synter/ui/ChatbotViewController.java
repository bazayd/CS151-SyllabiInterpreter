package com.app.cs151synter.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChatbotViewController {

    @FXML
    private TextField textInput;

    @FXML
    private ListView<Label> dialogHolder;

    @FXML
    private Button homeButton;

    @FXML
    private void submitResponse(ActionEvent event) {
        String response = "You: " + textInput.getText();

        Label chatBubble = new Label(response);
        dialogHolder.getItems().addAll(chatBubble);


        chatBubble = new Label(botResponse());
        dialogHolder.getItems().addAll(chatBubble);

        textInput.clear();
    }

    private String botResponse() {
        return "Bot: placeholder text";
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
