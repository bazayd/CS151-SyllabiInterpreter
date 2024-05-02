package com.app.cs151synter.ui;

import com.app.cs151synter.dataManipulation.IntentResponse;
import javafx.stage.Stage;

public interface ViewController {

    void buildAccordingToIntentResponse (IntentResponse o) throws IllegalArgumentException;
}
