package uol.compass.payments.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Application exception", description = "Error message returned by the application.")
public class ApplicationErrorMessage {

    @Schema(description = "The timestamp of the exception.", example = "2024-04-01T00:00:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private final Instant timestamp = Instant.now();

    @Schema(description = "The status code of the exception.", example = "422")
    private final Integer status;

    @Schema(description = "The error of the exception.", example = "Unprocessable Entity")
    private final String error;

    @Schema(description = "The message of the exception.", example = "Method argument not valid")
    private final String message;

    @Schema(description = "The method of the exception.", example = "POST")
    private final String method;

    @Schema(description = "The path of the exception.", example = "/v1/payments")
    private final String path;

    @Schema(description = "The errors of the exception.", example = "{\"customerId\":\"customerId must be informed\"}")
    private Map<String, String> errors = null;

    public ApplicationErrorMessage(@NotNull HttpStatus status, String message, String method, String path) {
        this.status = status.value();
        this.message = message;
        this.error = status.getReasonPhrase();
        this.method = method;
        this.path = path;
    }

    public ApplicationErrorMessage(@NotNull HttpStatus status, String message, String method, String path, @NotNull BindingResult result) {
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