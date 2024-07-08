package uol.compass.customer.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.customer.constants.Gender;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Customer response", description = "The response to a customer request.")
public class CustomerResponse {

    @Schema(description = "The identifier.", example = "1")
    private Long id;

    @Schema(description = "The cpf.", example = "855.365.292-00")
    private String cpf;

    @Schema(description = "The name.", example = "John")
    private String name;

    @Schema(description = "The gender", example = "MASCULINE")
    private Gender gender;

    @Schema(description = "The email.", example = "john@gmail.com")
    private String email;

    @Schema(description = "The photo url", example = "https://arnaldos3.s3.amazonaws.com/profile/8575fc57-3a9a-34fd-b974-2a9b1e983d57.gif")
    private String photo;

    @Schema(type = "string", description = "The birth date.", example = "01/01/2000", pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Schema(description = "The points.", example = "100")
    private Integer points;

}