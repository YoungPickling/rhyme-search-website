package lt.rimuok.exceptions;

/**
 * InputValidationException is a custom exception class (extends NullPointerException to dispose of try-catch blocks).
 * It is thrown when input validation fails due to invalid characters.
 */

public class InputValidationException extends NullPointerException{
    public InputValidationException(String message) {
        super(message);
    }
}