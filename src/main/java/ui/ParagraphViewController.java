package ui;

import customExceptions.IntentResponseAndViewControllerCollision;
import dataManipulation.IntentResponse;
import dataManipulation.ParagraphResponse;

public class ParagraphViewController implements ViewController {
    public static void main(String[] args) {

    }

    public void buildAccordingToIntentResponse(IntentResponse o) {
        if (!(o instanceof ParagraphResponse))
            throw new IntentResponseAndViewControllerCollision();
    }
}
