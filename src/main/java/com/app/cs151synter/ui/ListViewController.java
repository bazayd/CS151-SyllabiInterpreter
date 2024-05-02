package com.app.cs151synter.ui;

import com.app.cs151synter.dataContainers.Assignment;
import com.app.cs151synter.dataContainers.DatedSyllabusEntity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

public class ListViewController implements Initializable {

    @FXML
    private ListView<DatedSyllabusEntity> listView;
    @FXML
    private TextField textField;
    @FXML
    private Button homeButton;
    List<DatedSyllabusEntity> DatedSyllabusEntities;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    @FXML
    private void headHome() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("syllabusmain.fxml"));

        Scene scene = homeButton.getScene();
        Window window = scene.getWindow();
        Stage stage = (Stage) window;

        stage.setScene(new Scene(root));
    }
    @FXML
    protected void search() {
        String input = textField.getCharacters().toString();
        var filtered = DatedSyllabusEntities.stream().filter(s -> s.getTitle().contains(input)).toList();
        listView.getItems().clear();
        for (DatedSyllabusEntity a : filtered) {
            listView.getItems().add(a);
        }
    }
    public void setDatedSyllabusEntities(List<DatedSyllabusEntity> datedSyllabusEntities) {
        this.DatedSyllabusEntities = datedSyllabusEntities;
        Collections.sort(datedSyllabusEntities, new Comparator<DatedSyllabusEntity>() {
            @Override
            public int compare(DatedSyllabusEntity o1, DatedSyllabusEntity o2) {
                return o1.getDueDate().getTime().compareTo(o2.getDueDate().getTime());
            }
        });
        listView.getItems().addAll(DatedSyllabusEntities);
        listView.setOnMouseClicked(event -> {
            DatedSyllabusEntity b = listView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(b.getDescription());
            alert.showAndWait();
        });
    }

}
