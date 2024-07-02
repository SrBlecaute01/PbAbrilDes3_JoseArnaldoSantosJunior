package uol.compass.payments.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
public class CalculateRequest {

    private Long categoryId;
    private Integer value;

}