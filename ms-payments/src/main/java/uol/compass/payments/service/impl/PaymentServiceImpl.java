package uol.compass.payments.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;
import uol.compass.payments.service.PaymentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse getPayment(UUID id) {
        return null;
    }

    @Override
    public PagedModel<PaymentResponse> getPaymentsByCustomer(Long customerId, Pageable pageable) {
        return null;
    }
}
