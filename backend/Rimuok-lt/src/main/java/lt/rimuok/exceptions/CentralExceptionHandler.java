package lt.rimuok.exceptions;

import lt.rimuok.model.ErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyRhymeIndexException.class)
    public ResponseEntity<Object> handleRhymeIndexNotFound(final Exception exception, final WebRequest request) {
        HttpStatus status = HttpStatus.PRECONDITION_FAILED; // Error 412

        return handleExceptionInternal(
                exception,
                new ErrorModel("no records", status.value()),
                new HttpHeaders(),
                status,
                request);
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<Object> handleInvalidInput(final Exception exception, final WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN; // Error 403

        return handleExceptionInternal(
                exception,
                new ErrorModel("invalid characters", status.value()),
                new HttpHeaders(),
                status,
                request);
    }
}
