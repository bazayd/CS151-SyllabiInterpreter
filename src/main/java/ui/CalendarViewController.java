package ui;

import customExceptions.IntentResponseAndViewControllerCollision;
import dataManipulation.CalendarResponse;
import dataManipulation.IntentResponse;

public class CalendarViewController implements ViewController {
    public static void main(String[] args) {

    }

    public void buildAccordingToIntentResponse(IntentResponse o) {
        if (!(o instanceof CalendarResponse))
            throw new IntentResponseAndViewControllerCollision();

    }
}
