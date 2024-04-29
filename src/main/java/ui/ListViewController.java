package ui;

import dataContainers.DatedSyllabusEntity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ListViewController implements Initializable {
    @FXML
    private ListView<DatedSyllabusEntity> listView;
    @FXML
    private TextField textField;
    List<DatedSyllabusEntity> DatedSyllabusEntities;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    }

}