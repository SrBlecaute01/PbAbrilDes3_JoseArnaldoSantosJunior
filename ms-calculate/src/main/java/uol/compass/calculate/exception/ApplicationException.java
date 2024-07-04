package uol.compass.calculate.exception;

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

@Schema(
        name = "Application exception.",
        description = "The exception schema."
)
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationException {

    @Schema(description = "The timestamp of the exception.", example = "2024-04-01T00:00:00Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private final Instant timestamp = Instant.now();

    @Schema(description = "The status code of the exception.", example = "400")
    private final Integer status;

    @Schema(description = "The error of the exception.", example = "Bad Request")
    private final String error;

    @Schema(description = "The message of the exception.", example = "Required request body is missing")
    private final String message;

    @Schema(description = "The method of the exception.", example = "POST")
    private final String method;

    @Schema(description = "The path of the exception.", example = "/v1/calculate")
    private final String path;

    @Schema(description = "The errors of the exception.", example = "{\"value\":\"must be greater than or equal to 0\"}")
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