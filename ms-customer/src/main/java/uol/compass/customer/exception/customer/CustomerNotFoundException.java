package uol.compass.customer.exception.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "customer not found")
public class CustomerNotFoundException extends RuntimeException {


}