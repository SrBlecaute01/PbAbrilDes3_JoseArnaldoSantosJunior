package uol.compass.payments.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uol.compass.payments.dto.request.CalculateRequest;
import uol.compass.payments.dto.response.CalculateResponse;

@UtilityClass
public class CalculateUtil {

    @Getter
    private static final CalculateRequest request = new CalculateRequest(1L, 100);

    @Getter
    private static final CalculateResponse response = new CalculateResponse(1000);

}