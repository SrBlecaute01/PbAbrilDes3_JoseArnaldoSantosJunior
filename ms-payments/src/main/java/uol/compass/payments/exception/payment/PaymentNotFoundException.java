package uol.compass.payments.exception.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "payment not found")
public class PaymentNotFoundException extends RuntimeException {

}