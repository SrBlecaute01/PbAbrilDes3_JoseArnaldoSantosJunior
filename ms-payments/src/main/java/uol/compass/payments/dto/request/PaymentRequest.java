package uol.compass.payments.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Payment request", description = "Request body to create a payment.")
public class PaymentRequest {

    @Schema(description = "The customer id.", example = "1")
    @NotNull(message = "customerId must be informed.")
    private Long customerId;

    @Schema(description = "The category id.", example = "1")
    @NotNull(message = "categoryId must be informed.")
    private Long categoryId;

    @Schema(description = "The value of the payment.", example = "100")
    @NotNull(message = "value must be informed.")
    private Integer value;

}