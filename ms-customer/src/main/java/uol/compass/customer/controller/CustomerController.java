package uol.compass.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;
import uol.compass.customer.service.CustomerService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/customers")
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public CustomerResponse createCustomer(@Valid CustomerRequest request) {
        return this.service.createCustomer(request);
    }

}