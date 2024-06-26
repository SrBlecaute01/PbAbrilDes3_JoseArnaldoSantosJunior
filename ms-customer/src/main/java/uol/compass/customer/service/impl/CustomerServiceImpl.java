package uol.compass.customer.service.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;
import uol.compass.customer.exception.customer.CustomerAlreadyExistsException;
import uol.compass.customer.model.Customer;
import uol.compass.customer.repository.CustomerRepository;
import uol.compass.customer.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final ModelMapper mapper;

    @Override
    public CustomerResponse createCustomer(@NotNull CustomerRequest request) {
        if (this.repository.existsByEmailIgnoreCaseOrCpf(request.getEmail(), request.getCpf())) {
            throw new CustomerAlreadyExistsException();
        }

        final var customer = this.mapper.map(request, Customer.class);
        customer.setPoints(0);

        return this.mapper.map(this.repository.save(customer), CustomerResponse.class);
    }

}