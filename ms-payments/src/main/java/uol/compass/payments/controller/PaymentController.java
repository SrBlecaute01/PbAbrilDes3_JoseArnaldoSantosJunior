package uol.compass.payments.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;
import uol.compass.payments.service.PaymentService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest request) {
        return service.createPayment(request);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable("id") UUID id) {
        return service.getPayment(id);
    }

    @GetMapping("/user/{id}")
    public PagedModel<PaymentResponse> getPaymentsByCustomer(@PathVariable("id") Long customerId, @PageableDefault Pageable pageable) {
        return service.getPaymentsByCustomer(customerId, pageable);
    }

}
