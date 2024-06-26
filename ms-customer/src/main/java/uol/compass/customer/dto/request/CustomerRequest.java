package uol.compass.customer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import uol.compass.customer.constants.Gender;

import java.util.Date;

@Data
public class CustomerRequest {

    @NotBlank(message = "cpf must be informed")
    @CPF(message = "cpf must be valid")
    private String cpf;

    @NotBlank(message = "name must be informed")
    @Length(max = 255, message = "name must have a maximum of 255 characters")
    private String name;

    @NotNull(message = "gender must be informed")
    private Gender gender;

    @NotBlank(message = "email must be informed")
    @Email(message = "email must be valid")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "birthDate must be informed")
    private Date birthDate;

}