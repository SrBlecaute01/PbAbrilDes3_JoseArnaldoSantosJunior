package uol.compass.calculate.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uol.compass.calculate.dto.request.CalculateRequest;

@UtilityClass
public class CalculateUtil {

    @Getter
    private static final CalculateRequest request = new CalculateRequest(RuleUtil.getRule().getId(), 100);

}