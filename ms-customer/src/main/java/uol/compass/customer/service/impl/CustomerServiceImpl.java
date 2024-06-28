package uol.compass.customer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;
import uol.compass.customer.exception.customer.CustomerAlreadyExistsException;
import uol.compass.customer.exception.customer.CustomerNotFoundException;
import uol.compass.customer.model.Customer;
import uol.compass.customer.repository.CustomerRepository;
import uol.compass.customer.service.CustomerService;
import uol.compass.customer.service.FileUploadService;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final FileUploadService uploadService;

    private final ModelMapper mapper;

    @Override
    public CustomerResponse createCustomer(@NotNull CustomerRequest request) {
        if (this.repository.existsByEmailIgnoreCaseOrCpf(request.getEmail(), request.getCpf())) {
            throw new CustomerAlreadyExistsException();
        }

        final var fileName = UUID.nameUUIDFromBytes(request.getEmail().toLowerCase().getBytes(StandardCharsets.UTF_8));
        final var url = this.uploadService.uploadBase64File(fileName.toString(), request.getPhoto());
        final var customer = this.mapper.map(request, Customer.class);

        customer.setPhoto(url);
        customer.setPoints(0);

        return this.mapper.map(this.repository.save(customer), CustomerResponse.class);
    }

    @Override
    public CustomerResponse getCustomer(@NotNull Long id) {
        return this.repository.findById(id)
                .map(customer -> this.mapper.map(customer, CustomerResponse.class))
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public CustomerResponse updateCustomer(@NotNull Long id, @NotNull CustomerRequest request) {
        final var customer = this.repository.findById(id).orElseThrow(CustomerNotFoundException::new);
        if (this.repository.existsByEmailOrCpf(request.getEmail(), request.getCpf(), id)) {
            throw new CustomerAlreadyExistsException();
        }

        final var fileName = UUID.nameUUIDFromBytes(request.getEmail().toLowerCase().getBytes(StandardCharsets.UTF_8));
        final var url = this.uploadService.uploadBase64File(fileName.toString(), request.getPhoto());

        customer.setCpf(request.getCpf());
        customer.setEmail(request.getEmail());
        customer.setPhoto(url);
        customer.setName(request.getName());
        customer.setGender(request.getGender());
        customer.setBirthDate(request.getBirthDate());

        return this.mapper.map(this.repository.save(customer), CustomerResponse.class);
    }

    @Override
    public void deleteCustomer(@NotNull Long id) {
        final int affected = this.repository.deleteCustomerById(id);
        if (affected <= 0) throw new CustomerNotFoundException();
    }

}