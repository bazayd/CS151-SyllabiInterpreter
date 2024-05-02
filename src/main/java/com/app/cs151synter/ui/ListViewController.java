package com.app.cs151synter.ui;

import com.app.cs151synter.dataContainers.DatedSyllabusEntity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

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
        ClassSwitcher.switchScene(homeButton, "syllabusmain.fxml");
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