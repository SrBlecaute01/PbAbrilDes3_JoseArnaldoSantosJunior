package uol.compass.payments.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPayment(UUID id);

    PagedModel<PaymentResponse> getPaymentsByCustomer(Long customerId, Pageable pageable);

}