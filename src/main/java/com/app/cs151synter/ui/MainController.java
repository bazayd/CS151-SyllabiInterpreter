package com.app.cs151synter.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //List<DatedSyllabusEntity> a = new ArrayList<DatedSyllabusEntity>();
        //for(int i = 0; i < 50; i++) {
        //    a.add(new Assignment("fart", "poo"));
        //}
        //ListViewController b = new ListViewController();
        // b.setDatedSyllabusEntities(a);
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("ListView.fxml"));
        Parent root = fxmlloader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
