package uol.compass.payments.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class ApplicationException extends RuntimeException {

    private Instant timestamp = Instant.now();
    private Integer status;

    private String error;
    private String message;
    private String method;

    private Map<String, String> errors = null;

    public ApplicationException(@NotNull HttpStatus status, String message, String method) {
        this.status = status.value();
        this.message = message;
        this.error = status.getReasonPhrase();
        this.method = method;
    }

}