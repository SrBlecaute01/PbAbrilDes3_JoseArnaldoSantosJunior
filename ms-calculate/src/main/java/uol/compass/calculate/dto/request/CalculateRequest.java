package uol.compass.calculate.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CalculateRequest {

    @NotNull(message = "categoryId must be informed")
    private Long categoryId;

    @NotNull(message = "value must be informed")
    private Integer value;

}