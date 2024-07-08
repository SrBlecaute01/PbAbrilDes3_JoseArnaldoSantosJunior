package uol.compass.customer.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid file type")
public class InvalidFileTypeException extends RuntimeException {


}
