package uol.compass.payments.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Schema(name = "Payment response", description = "The response for payment operations.")
public class PaymentResponse {

    @Schema(description = "The payment id.", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "The customer id.", example = "1")
    private Long customerId;

    @Schema(description = "The category id.", example = "1")
    private Long categoryId;

    @Schema(description = "The value of the payment.", example = "100")
    private Integer value;

}