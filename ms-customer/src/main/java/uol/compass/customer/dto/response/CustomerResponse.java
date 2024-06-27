package uol.compass.customer.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import uol.compass.customer.constants.Gender;

import java.util.Date;

@Data
public class CustomerResponse {

    private Long id;
    private String cpf;
    private String name;
    private Gender gender;
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private Integer points;

}