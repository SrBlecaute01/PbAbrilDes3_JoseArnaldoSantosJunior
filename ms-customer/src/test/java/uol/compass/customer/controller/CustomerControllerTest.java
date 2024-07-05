package uol.compass.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.exception.customer.CustomerAlreadyExistsException;
import uol.compass.customer.exception.customer.CustomerNotFoundException;
import uol.compass.customer.exception.handler.RestExceptionHandler;
import uol.compass.customer.service.CustomerService;
import uol.compass.customer.util.CustomerUtil;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@Import({CustomerController.class, RestExceptionHandler.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Customer controller")
public class CustomerControllerTest {

    private static final String PATH = "/v1/customers";

    private MockMvc mockMvc;

    @Autowired private WebApplicationContext webContext;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private CustomerService service;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
    }

    @Test
    @DisplayName("Controller create customer")
    void testCreateCustomer() throws Exception {
        final var request = CustomerUtil.getRequest();
        final var response = CustomerUtil.getResponse();

        when(this.service.createCustomer(any())).thenReturn(CustomerUtil.getResponse());

        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.cpf").value(response.getCpf()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("Controller create customer already exists")
    void testCreateCustomerAlreadyExists() throws Exception {
        final var request = CustomerUtil.getRequest();

        when(this.service.createCustomer(any())).thenThrow(new CustomerAlreadyExistsException());
        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Controller create customer invalid email")
    void testCreateCustomerInvalidEmail() throws Exception {
        final var value = CustomerUtil.getRequest();
        final var request = new CustomerRequest(
                value.getCpf(),
                value.getName(),
                value.getGender(),
                null,
                value.getPhoto(),
                value.getBirthDate());

        this.mockMvc.perform(post(PATH)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Controller get customer")
    void testGetCustomer() throws Exception {
        final var customer = CustomerUtil.getResponse();
        final var response = CustomerUtil.getResponse();

        when(this.service.getCustomer(any())).thenReturn(response);
        this.mockMvc.perform(get(PATH + "/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.cpf").value(response.getCpf()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("Controller get customer not found")
    void testGetCustomerNotFound() throws Exception {
        final var customer = CustomerUtil.getResponse();

        when(this.service.getCustomer(any())).thenThrow(new CustomerNotFoundException());
        this.mockMvc.perform(get(PATH + "/{id}", customer.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller update customer")
    void testUpdateCustomer() throws Exception {
        final var request = CustomerUtil.getUpdateRequest();
        final var response = CustomerUtil.getUpdateResponse();

        when(this.service.updateCustomer(any(), any())).thenReturn(response);
        this.mockMvc.perform(put(PATH + "/{id}", response.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.cpf").value(response.getCpf()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("Controller update customer already exists")
    void testUpdateCustomerAlreadyExists() throws Exception {
        final var request = CustomerUtil.getUpdateRequest();
        final var response = CustomerUtil.getUpdateResponse();

        when(this.service.updateCustomer(any(), any())).thenThrow(new CustomerAlreadyExistsException());
        this.mockMvc.perform(put(PATH + "/{id}", response.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Controller update customer invalid email")
    void testUpdateCustomerInvalidEmail() throws Exception {
        final var customer = CustomerUtil.getUpdatedCustomer();
        final var value = CustomerUtil.getUpdateRequest();
        final var request = new CustomerRequest(
                value.getCpf(),
                value.getName(),
                value.getGender(),
                null,
                value.getPhoto(),
                value.getBirthDate());

        this.mockMvc.perform(put(PATH + "/{id}", customer.getId())
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Controller delete customer")
    void testDeleteCustomer() throws Exception {
        final var customer = CustomerUtil.getResponse();
        this.mockMvc.perform(delete(PATH + "/{id}", customer.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Controller delete customer not found")
    void testDeleteCustomerNotFound() throws Exception {
        final var customer = CustomerUtil.getResponse();

        doThrow(new CustomerNotFoundException()).when(this.service).deleteCustomer(any());
        this.mockMvc.perform(delete(PATH + "/{id}", customer.getId()))
                .andExpect(status().isNotFound());
    }


}
