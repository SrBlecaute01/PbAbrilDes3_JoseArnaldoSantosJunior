package uol.compass.calculate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Calculate response.", description = "The response schema of a calculate request.")
public class CalculateResponse {

    @Schema(description = "The calculated value.", example = "1000")
    private Integer total;

}