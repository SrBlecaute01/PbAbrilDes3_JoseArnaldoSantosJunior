package uol.compass.payments.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uol.compass.payments.exception.ApplicationErrorMessage;
import uol.compass.payments.exception.ApplicationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationErrorMessage> methodArgumentNotValid(@NotNull HttpServletRequest request, BindingResult result){
        final var status = HttpStatus.UNPROCESSABLE_ENTITY;
        final var exception = new ApplicationErrorMessage(status, "Method argument not valid", request.getMethod(), request.getRequestURI(), result);
        return ResponseEntity.status(status).body(exception);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApplicationErrorMessage> applicationException(@NotNull HttpServletRequest request, ApplicationException exception){
        final var status = exception.getStatus();
        final var error = new ApplicationErrorMessage(HttpStatus.valueOf(status), exception.getMessage(), request.getMethod(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}