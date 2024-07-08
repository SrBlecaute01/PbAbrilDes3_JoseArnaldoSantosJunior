package uol.compass.calculate.exception.rule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "rule with this category already exists.")
public class RuleAlreadyExistsException extends RuntimeException {

}