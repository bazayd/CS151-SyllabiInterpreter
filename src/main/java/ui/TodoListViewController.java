package ui;

import customExceptions.IntentResponseAndViewControllerCollision;
import dataManipulation.IntentResponse;
import dataManipulation.ListResponse;

public class TodoListViewController extends ListViewController implements ViewController {
    public static void main(String[] args) {

    }

    @Override
    public void buildAccordingToIntentResponse (IntentResponse o) {
        if (!(o instanceof ListResponse))
            throw new IntentResponseAndViewControllerCollision();
    }
}
