package uol.compass.payments.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.assertj.core.util.DateUtil;
import uol.compass.payments.constants.Gender;
import uol.compass.payments.dto.response.CustomerResponse;

@UtilityClass
public class CustomerUtil {

    @Getter
    private static final CustomerResponse response = new CustomerResponse(
            1L,
            "698.354.265-70",
            "John",
            Gender.MASCULINE,
            "john@gmail.com",
            "https://test.s3.amazonaws.com/profile/test.jpeg",
            DateUtil.parse("1990-01-01"),
            0);

}