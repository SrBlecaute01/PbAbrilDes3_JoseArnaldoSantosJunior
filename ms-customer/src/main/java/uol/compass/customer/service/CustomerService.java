package uol.compass.customer.service;

import org.jetbrains.annotations.NotNull;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse createCustomer(@NotNull CustomerRequest request);

    CustomerResponse getCustomer(@NotNull Long id);

    CustomerResponse updateCustomer(@NotNull Long id, @NotNull CustomerRequest request);

    void deleteCustomer(@NotNull Long id);

}