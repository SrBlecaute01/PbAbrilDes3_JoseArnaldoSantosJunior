package uol.compass.customer.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uol.compass.customer.exception.ApplicationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationException> methodArgumentNotValid(@NotNull HttpServletRequest request, BindingResult result){
        final var status = HttpStatus.UNPROCESSABLE_ENTITY;
        final var exception = new ApplicationException(status, "Method argument not valid", request.getMethod(), request.getRequestURI(), result);
        return ResponseEntity.status(status).body(exception);
    }

}