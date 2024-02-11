package alex.validation.rest;

import alex.validation.model.ApiMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice()
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler({ MethodArgumentNotValidException .class})
    public ResponseEntity<ApiMessage> handleMethodArgumentNotValidEx(MethodArgumentNotValidException exception){

        //var violation = new ConstraintViolationImpl(exception.getMessage(), null);
        return response(HttpStatus.BAD_REQUEST, exception, null);

    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiMessage> handleConstraintViolations(
            ConstraintViolationException exception) {
        var violations = List.copyOf(exception.getConstraintViolations());

        return response(
                HttpStatus.BAD_REQUEST,
                exception, violations);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiMessage> handleConstraintViolations(
            RuntimeException exception) {

        return response(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception, null);
    }


    private ResponseEntity<ApiMessage> response(HttpStatus status, Exception ex, List<ConstraintViolation<?>> violations) {

        List<String> errors = new ArrayList<>();
        if (violations != null) {
            violations.stream().map(ConstraintViolation::getMessage).forEach(errors::add);
        }

        ApiMessage apiError = ApiMessage.builder()
                .message(ex.getMessage())
                .errors(errors)
                .errorType(ex.getClass().toGenericString()).build();

        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(apiError);
    }




}
