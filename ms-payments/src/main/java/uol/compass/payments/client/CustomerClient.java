package uol.compass.payments.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uol.compass.payments.configuration.FeignConfiguration;
import uol.compass.payments.dto.response.CustomerResponse;

@FeignClient(name = "customer", configuration = FeignConfiguration.class)
public interface CustomerClient {

    @GetMapping(value = "/v1/customers/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CustomerResponse getCustomer(@PathVariable Long id);

}