package uol.compass.customer.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uol.compass.customer.dto.request.PointsRequest;

@UtilityClass
public class PointsUtil {

    @Getter
    private static final PointsRequest request = new PointsRequest(1L, 10);

}
