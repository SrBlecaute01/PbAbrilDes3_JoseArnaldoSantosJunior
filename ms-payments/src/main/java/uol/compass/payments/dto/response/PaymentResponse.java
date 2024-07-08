package uol.compass.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Schema(description = "The creation date of the payment.", example = "05/07/2024")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

}