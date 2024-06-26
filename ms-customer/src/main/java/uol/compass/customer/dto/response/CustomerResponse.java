package uol.compass.customer.dto.response;

import lombok.Data;
import uol.compass.customer.constants.Gender;

@Data
public class CustomerResponse {

    private Long id;
    private String cpf;
    private String name;
    private Gender gender;
    private String email;
    private Integer points;

}