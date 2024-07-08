package uol.compass.payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.exception.ApplicationException;
import uol.compass.payments.exception.handler.RestExceptionHandler;
import uol.compass.payments.exception.payment.PaymentNotFoundException;
import uol.compass.payments.service.PaymentService;
import uol.compass.payments.util.PaymentUtil;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import({PaymentController.class, RestExceptionHandler.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Payment controller")
public class PaymentControllerTest {

    private static final String PATH = "/v1/payments";

    private MockMvc mockMvc;

    @Autowired private WebApplicationContext webContext;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private PaymentService service;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
    }

    @Test
    @DisplayName("Controller create payment")
    void testCreatePayment() throws Exception {
        final var request = PaymentUtil.getRequest();
        final var response = PaymentUtil.getResponse();

        when(this.service.createPayment(any())).thenReturn(PaymentUtil.getResponse());

        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.customerId").value(response.getCustomerId()))
                .andExpect(jsonPath("$.categoryId").value(response.getCategoryId()))
                .andExpect(jsonPath("$.value").value(response.getValue()));
    }

    @Test
    @DisplayName("Controller create payment invalid customer")
    void testCreatePaymentInvalidCustomer() throws Exception {
        doThrow(new ApplicationException(HttpStatus.NOT_FOUND, "customer not found", "POST")).when(this.service).createPayment(any());

        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(PaymentUtil.getRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller create payment invalid request")
    void testCreatePaymentInvalidRequest() throws Exception {
        final var value = PaymentUtil.getRequest();
        final var request = new PaymentRequest(null, value.getCategoryId(), value.getValue());

        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Controller get payment")
    void testGetPayment() throws Exception {
        final var response = PaymentUtil.getResponse();

        when(this.service.getPayment(any())).thenReturn(PaymentUtil.getResponse());

        this.mockMvc.perform(get(PATH + "/{id}", response.getId())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.customerId").value(response.getCustomerId()))
                .andExpect(jsonPath("$.categoryId").value(response.getCategoryId()))
                .andExpect(jsonPath("$.value").value(response.getValue()));
    }

    @Test
    @DisplayName("Controller get payment not found")
    void testGetPaymentNotFound() throws Exception {
        final var id = PaymentUtil.getResponse().getId();

        when(this.service.getPayment(any())).thenThrow(new PaymentNotFoundException());

        this.mockMvc.perform(get(PATH + "/{id}", id)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller get payments by customer")
    void testGetPaymentsByCustomer() throws Exception {
        final var response = PaymentUtil.getResponse();
        final var pageable = new PagedModel<>(new PageImpl<>(List.of(response)));

        when(this.service.getPaymentsByCustomer(any(), any())).thenReturn(pageable);

        final var metadata = pageable.getMetadata();
        Assertions.assertNotNull(metadata);

        this.mockMvc.perform(get(PATH + "/user/{id}", response.getCustomerId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(PaymentUtil.getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(response.getId().toString()))
                .andExpect(jsonPath("$.content[0].categoryId").value(response.getCategoryId()))
                .andExpect(jsonPath("$.content[0].customerId").value(response.getCustomerId()))
                .andExpect(jsonPath("$.content[0].value").value(response.getValue()))
                .andExpect(jsonPath("$.page.totalElements").value(metadata.totalElements()));
    }

}