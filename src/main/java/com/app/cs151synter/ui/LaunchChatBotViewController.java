package com.app.cs151synter.ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.util.logging.Logger;


public class LaunchChatBotViewController {
    Logger logger = Logger.getLogger(LaunchChatBotViewController.class.getName());

    @FXML
    private MenuItem upLoadFromFileSystemButton;
    @FXML
    private Label knownProfsList;

    public void onUploadFromFileSystemButton(ActionEvent actionEvent) {
        logger.fine ("on upload from file system.. " );

    }
}
