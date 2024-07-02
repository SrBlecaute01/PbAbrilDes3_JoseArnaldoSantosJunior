package uol.compass.payments.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import uol.compass.payments.client.CalculateClient;
import uol.compass.payments.client.CustomerClient;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;
import uol.compass.payments.exception.payment.PaymentNotFoundException;
import uol.compass.payments.model.Payment;
import uol.compass.payments.repository.PaymentRepository;
import uol.compass.payments.service.PaymentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final CustomerClient customerClient;
    private final CalculateClient calculateClient;
    private final ModelMapper mapper;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        this.customerClient.getCustomer(request.getCustomerId());

        final var payment = this.repository.save(this.mapper.map(request, Payment.class));
        return this.mapper.map(payment, PaymentResponse.class);
    }

    @Override
    public PaymentResponse getPayment(UUID id) {
        return this.repository.findById(id)
                .map(payment -> this.mapper.map(payment, PaymentResponse.class))
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public PagedModel<PaymentResponse> getPaymentsByCustomer(Long customerId, Pageable pageable) {
        return null;
    }
}
