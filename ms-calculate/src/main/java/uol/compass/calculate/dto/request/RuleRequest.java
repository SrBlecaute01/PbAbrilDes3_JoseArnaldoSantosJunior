package uol.compass.calculate.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class RuleRequest {

    @NotBlank(message = "category must be informed")
    @Length(max = 255, message = "category must have a maximum of 255 characters")
    private String category;

    @NotNull(message = "parity must be informed")
    private Integer parity;

}