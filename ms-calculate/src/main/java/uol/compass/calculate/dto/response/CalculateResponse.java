package uol.compass.calculate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Calculate response.",
        description = "The response schema of a calculate request.")
@Data
@AllArgsConstructor
public class CalculateResponse {

    @Schema(description = "The calculated value.", example = "1000")
    private Integer total;

}