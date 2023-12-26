package lt.rimuok.exceptions;

import lt.rimuok.model.ErrorModel;
import lt.rimuok.model.SuggestionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * CentralExceptionHandler is a global exception handler for handling specific exceptions
 * in a centralized manner. It extends ResponseEntityExceptionHandler and provides custom
 * handling for EmptyRhymeIndexException and InputValidationException.
 */

@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles EmptyRhymeIndexException and returns a ResponseEntity with a predefined
     * error message and status code.
     *
     * @param exception The EmptyRhymeIndexException to be handled.
     * @param request   The WebRequest associated with the exception.
     * @return ResponseEntity containing an ErrorModel with a specific error message and status code.
     */

    @ExceptionHandler(EmptyRhymeIndexException.class)
    public ResponseEntity<Object> handleRhymeIndexNotFound(final EmptyRhymeIndexException exception, final WebRequest request) {
        HttpStatus status = HttpStatus.OK; // status 200

        return handleExceptionInternal(
                exception,
                new SuggestionModel(exception.getWord()), // new ErrorModel("no records", status.value())
                new HttpHeaders(),
                status,
                request);
    }

    /**
     * Handles InputValidationException and returns a ResponseEntity with a predefined
     * error message and status code.
     *
     * @param exception The InputValidationException to be handled.
     * @param request   The WebRequest associated with the exception.
     * @return ResponseEntity containing an ErrorModel with a specific error message and status code.
     */

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Object> handleInvalidInput(final Exception exception, final WebRequest request) {
        HttpStatus status = HttpStatus.PRECONDITION_FAILED; // Error 412

        return handleExceptionInternal(
                exception,
                new ErrorModel("invalid characters", status.value()),
                new HttpHeaders(),
                status,
                request);
    }
}
