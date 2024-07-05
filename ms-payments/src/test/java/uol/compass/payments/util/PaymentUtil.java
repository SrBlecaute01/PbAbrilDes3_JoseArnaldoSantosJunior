package uol.compass.payments.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;
import uol.compass.payments.model.Payment;

import java.util.Date;
import java.util.UUID;

@UtilityClass
public class PaymentUtil {

    @Getter
    private static final Payment payment = new Payment(UUID.randomUUID(), 1L, 1L, 10, new Date());

    @Getter
    private static final PaymentResponse response = new PaymentResponse(
            payment.getId(),
            payment.getCustomerId(),
            payment.getCategoryId(),
            payment.getValue(),
            payment.getCreatedAt());

    @Getter
    private static final PaymentRequest request = new PaymentRequest(
            payment.getCustomerId(),
            payment.getCategoryId(),
            payment.getValue());

}
