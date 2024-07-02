package uol.compass.payments.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.payments.constants.Gender;

import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerResponse {

    private Long id;
    private String cpf;
    private String name;
    private Gender gender;
    private String email;
    private String photo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    private Integer points;

}