package uol.compass.payments.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentResponse {

    private UUID id;
    private Long customerId;
    private Long categoryId;
    private Integer value;

}