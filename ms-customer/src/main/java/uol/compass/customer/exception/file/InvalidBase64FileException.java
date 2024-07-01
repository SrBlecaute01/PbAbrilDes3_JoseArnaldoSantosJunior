package uol.compass.customer.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "not a valid base64 string")
public class InvalidBase64FileException extends RuntimeException {


}
