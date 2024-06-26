package uol.compass.customer.service;

import org.jetbrains.annotations.NotNull;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(@NotNull CustomerRequest request);

}