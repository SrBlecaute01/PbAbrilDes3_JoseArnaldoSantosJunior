package uol.compass.payments.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PaymentRequest {

    @NotNull(message = "customerId must be informed.")
    private Long customerId;

    @NotNull(message = "categoryId must be informed.")
    private Long categoryId;

    @NotNull(message = "value must be informed.")
    private Integer value;

}