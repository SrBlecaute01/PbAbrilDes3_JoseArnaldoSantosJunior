package uol.compass.calculate.exception.rule;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "rule not found")
public class RuleNotFoundException extends RuntimeException {

}