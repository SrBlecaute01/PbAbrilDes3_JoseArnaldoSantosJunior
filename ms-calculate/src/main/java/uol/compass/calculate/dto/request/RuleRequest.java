package uol.compass.calculate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@NoArgsConstructor
@Schema(name = "Rule request", description = "Request body to create or update a rule.")
public class RuleRequest {

    @Schema(description = "The category name.", maxLength = 255, example = "electronic")
    @NotBlank(message = "category must be informed")
    @Length(max = 255, message = "category must have a maximum of 255 characters")
    private String category;

    @Schema(description = "The parity value.", example = "10")
    @NotNull(message = "parity must be informed")
    private Integer parity;

}