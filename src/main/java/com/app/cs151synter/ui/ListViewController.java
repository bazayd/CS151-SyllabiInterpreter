package com.app.cs151synter.ui;

import com.app.cs151synter.customExceptions.IntentResponseAndViewControllerCollisionException;
import com.app.cs151synter.dataManipulation.IntentResponse;
import com.app.cs151synter.dataManipulation.ListResponse;

public class ListViewController implements ViewController {
    public static void main(String[] args) {

    }

    public void buildAccordingToIntentResponse(IntentResponse o) {
        if (!(o instanceof ListResponse))
            throw new IntentResponseAndViewControllerCollisionException();

    }
}
