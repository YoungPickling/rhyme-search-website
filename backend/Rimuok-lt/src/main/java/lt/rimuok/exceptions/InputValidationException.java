package lt.rimuok.exceptions;

public class InputValidationException extends NullPointerException{
    public InputValidationException(String message) {
        super(message);
    }
}