package uol.compass.customer.exception.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "customer with email or cpf already exists")
public class CustomerAlreadyExistsException extends RuntimeException {

}