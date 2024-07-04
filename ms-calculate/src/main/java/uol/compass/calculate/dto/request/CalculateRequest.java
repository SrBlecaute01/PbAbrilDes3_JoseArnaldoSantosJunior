package uol.compass.calculate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Schema(name = "Calculate request.", description = "Request schema to calculate a value.")
public class CalculateRequest {

    @Schema(description = "The category id.", example = "1")
    @NotNull(message = "categoryId must be informed")
    private Long categoryId;

    @Schema(description = "The value to be calculated.", example = "100")
    @NotNull(message = "value must be informed")
    private Integer value;

}