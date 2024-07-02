package uol.compass.payments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import uol.compass.payments.configuration.FeignConfiguration;
import uol.compass.payments.dto.request.CalculateRequest;
import uol.compass.payments.dto.response.CalculateResponse;

@FeignClient(name = "calculate", configuration = FeignConfiguration.class)
public interface CalculateClient {

    @PostMapping(value = "/v1/calculate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CalculateResponse calculate(CalculateRequest request);

}