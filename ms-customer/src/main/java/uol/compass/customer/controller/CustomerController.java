package uol.compass.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
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
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        final var customer = this.service.createCustomer(request);
        final var uri = UriComponentsBuilder.fromPath("/v1/customers/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).body(customer);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return this.service.getCustomer(id);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return this.service.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        this.service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}