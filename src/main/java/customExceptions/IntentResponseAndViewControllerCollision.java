package customExceptions;

public class IntentResponseAndViewControllerCollision extends IllegalArgumentException {
    public IntentResponseAndViewControllerCollision () {
        super ("Intent response and view controller collided. ");
    }
}
