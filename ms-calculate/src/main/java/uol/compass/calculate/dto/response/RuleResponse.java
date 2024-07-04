package uol.compass.calculate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Rule response", description = "The response to rule requests.")
@Data
@NoArgsConstructor
public class RuleResponse {

    @Schema(description = "The identifier.", example = "1")
    private Long id;

    @Schema(description = "The category.", example = "electronic")
    private String category;

    @Schema(description = "The parity.", example = "10")
    private Integer parity;

}