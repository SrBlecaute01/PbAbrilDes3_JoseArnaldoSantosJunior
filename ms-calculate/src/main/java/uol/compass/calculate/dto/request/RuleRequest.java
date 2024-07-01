package uol.compass.calculate.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@NoArgsConstructor
public class RuleRequest {

    @NotBlank(message = "category must be informed")
    @Length(max = 255, message = "category must have a maximum of 255 characters")
    private String category;

    @NotNull(message = "parity must be informed")
    private Integer parity;

}