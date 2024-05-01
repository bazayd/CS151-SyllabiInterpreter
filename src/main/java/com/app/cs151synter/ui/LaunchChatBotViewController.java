package com.app.cs151synter.ui;
import com.app.cs151synter.Main;
import com.app.cs151synter.dataContainers.InfoContainer;
import com.app.cs151synter.dataManipulation.PDFParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;


public class LaunchChatBotViewController {
    Logger logger = Logger.getLogger(Main.class.getName());
    @FXML
    private VBox root;
    @FXML
    private MenuItem upLoadFromFileSystemButton;
    @FXML
    private Label knownProfsList;

    public void onUploadFromFileSystemButton(ActionEvent actionEvent) {
        logger.fine ("on upload from file system.. " );
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setFileFilter(new FileNameExtensionFilter("*.pdf", "pdf")); // TODO figure out how to only accept pdfs
        fileChooser.setTitle("Select syllabus..");
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
        if (file == null)
            return;
        String filePath = file.toString();
        try {
            InfoContainer.addSyllabus (PDFParser.generateSyllabus(filePath));
        } catch (IOException | ExecutionException | InterruptedException e) {
            logger.severe("!!file upload failed!!");
            logger.severe(e.toString());
        }
    }
}
