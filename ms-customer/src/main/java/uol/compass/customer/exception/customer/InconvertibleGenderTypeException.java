package uol.compass.customer.exception.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "gender cannot be converted")
public class InconvertibleGenderTypeException extends RuntimeException {


}