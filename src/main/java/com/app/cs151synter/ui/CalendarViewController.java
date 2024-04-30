package com.app.cs151synter.ui;

import com.app.cs151synter.customExceptions.IntentResponseAndViewControllerCollisionException;
import com.app.cs151synter.dataManipulation.CalendarResponse;
import com.app.cs151synter.dataManipulation.IntentResponse;

public class CalendarViewController implements ViewController {
    public static void main(String[] args) {

    }

    public void buildAccordingToIntentResponse(IntentResponse o) {
        if (!(o instanceof CalendarResponse))
            throw new IntentResponseAndViewControllerCollisionException();

    }
}
