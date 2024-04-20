package customExceptions;

public class IntentResponseAndViewControllerCollisionException extends IllegalArgumentException {
    public IntentResponseAndViewControllerCollisionException () {
        super ("Intent response and view controller collided. ");
    }
}
