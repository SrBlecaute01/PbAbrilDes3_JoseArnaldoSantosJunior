package uol.compass.calculate.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private final Instant timestamp = Instant.now();
    private final Integer status;

    private final String error;
    private final String message;
    private final String method;
    private final String path;

    private Map<String, String> errors = null;

    public ApplicationException(@NotNull HttpStatus status, String message, String method, String path) {
        this.status = status.value();
        this.message = message;
        this.error = status.getReasonPhrase();
        this.method = method;
        this.path = path;
    }

    public ApplicationException(@NotNull HttpStatus status, String message, String method, String path, @NotNull BindingResult result) {
        this.status = status.value();
        this.message = message;
        this.error = status.getReasonPhrase();
        this.method = method;
        this.path = path;

        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()){
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}