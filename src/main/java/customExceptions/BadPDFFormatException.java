package customExceptions;

public class BadPDFFormatException extends IllegalArgumentException {
    public BadPDFFormatException () {
        super ("bad pdf format. ");
    }
}
