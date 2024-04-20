package ui;

import customExceptions.IntentResponseAndViewControllerCollisionException;
import dataManipulation.IntentResponse;
import dataManipulation.ListResponse;

public class TodoListViewController extends ListViewController implements ViewController {
    public static void main(String[] args) {

    }

    @Override
    public void buildAccordingToIntentResponse (IntentResponse o) {
        if (!(o instanceof ListResponse))
            throw new IntentResponseAndViewControllerCollisionException();
    }
}
