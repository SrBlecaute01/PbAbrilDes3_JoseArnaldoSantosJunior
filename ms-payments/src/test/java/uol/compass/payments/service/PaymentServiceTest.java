package uol.compass.payments.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.payments.client.CalculateClient;
import uol.compass.payments.client.CustomerClient;
import uol.compass.payments.configuration.MapperConfiguration;
import uol.compass.payments.configuration.ObjectMapperConfiguration;
import uol.compass.payments.exception.payment.PaymentNotFoundException;
import uol.compass.payments.repository.PaymentRepository;
import uol.compass.payments.service.impl.PaymentServiceImpl;
import uol.compass.payments.util.CalculateUtil;
import uol.compass.payments.util.CustomerUtil;
import uol.compass.payments.util.PaymentUtil;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Messaging Service")
@ContextConfiguration(classes= {MapperConfiguration.class, ObjectMapperConfiguration.class})
public class PaymentServiceTest {

    private PaymentService service;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private CalculateClient calculateClient;

    @Mock
    private MessagingService messagingService;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    void setup() {
        this.service = new PaymentServiceImpl(this.paymentRepository, this.customerClient, this.calculateClient, this.messagingService, this.mapper);
    }

    @Test
    @DisplayName("Service create payment")
    void testCreatePayment() {
        final var payment = PaymentUtil.getResponse();

        when(this.customerClient.getCustomer(anyLong())).thenReturn(CustomerUtil.getResponse());
        when(this.paymentRepository.save(any())).thenReturn(PaymentUtil.getPayment());
        when(this.calculateClient.calculate(any())).thenReturn(CalculateUtil.getResponse());

        final var response = this.service.createPayment(PaymentUtil.getRequest());

        Assertions.assertEquals(payment.getId(), response.getId());
        Assertions.assertEquals(payment.getCustomerId(), response.getCustomerId());
        Assertions.assertEquals(payment.getCategoryId(), response.getCategoryId());
    }

    @Test
    @DisplayName("Service get payment")
    void testGetPayment() {
        final var payment = PaymentUtil.getResponse();
        when(this.paymentRepository.findById(any())).thenReturn(Optional.of(PaymentUtil.getPayment()));

        final var response = this.service.getPayment(payment.getId());

        Assertions.assertEquals(payment.getId(), response.getId());
        Assertions.assertEquals(payment.getCustomerId(), response.getCustomerId());
        Assertions.assertEquals(payment.getCategoryId(), response.getCategoryId());
    }

    @Test
    @DisplayName("Service get payment not found")
    void testGetPaymentNotFound() {
        when(this.paymentRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(PaymentNotFoundException.class, () -> this.service.getPayment(PaymentUtil.getResponse().getId()));
    }

    @Test
    @DisplayName("Service get payments by customer")
    void testGetPaymentsByCustomer() {
        final var payment = PaymentUtil.getPayment();
        final var pageable = new PageImpl<>(List.of(payment));

        when(this.customerClient.getCustomer(anyLong())).thenReturn(CustomerUtil.getResponse());
        when(this.paymentRepository.findByCustomerId(anyLong(), any())).thenReturn(pageable);

        final var response = this.service.getPaymentsByCustomer(payment.getCustomerId(), mock());

        Assertions.assertNotNull(response.getMetadata());
        Assertions.assertEquals(1, response.getMetadata().totalElements());
        Assertions.assertEquals(payment.getId(), response.getContent().get(0).getId());
        Assertions.assertEquals(payment.getCategoryId(), response.getContent().get(0).getCategoryId());
        Assertions.assertEquals(payment.getValue(), response.getContent().get(0).getValue());
    }

}