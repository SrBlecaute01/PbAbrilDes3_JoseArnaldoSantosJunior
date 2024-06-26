package uol.compass.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import uol.compass.customer.constants.Gender;

@Data
public class CustomerRequest {

    @CPF(message = "cpf must be valid")
    private String cpf;

    @NotBlank(message = "name must be informed")
    @Max(value = 255, message = "name must have a maximum of 255 characters")
    private String name;

    @NotNull(message = "gender must be informed")
    private Gender gender;

    @Email(message = "email must be valid")
    private String email;

}