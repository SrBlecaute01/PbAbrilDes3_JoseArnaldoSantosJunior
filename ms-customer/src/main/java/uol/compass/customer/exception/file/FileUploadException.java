package uol.compass.customer.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "file cannot be uploaded")
public class FileUploadException extends RuntimeException {


}
