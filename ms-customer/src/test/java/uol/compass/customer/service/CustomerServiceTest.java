package uol.compass.customer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.customer.configuration.MapperConfiguration;
import uol.compass.customer.exception.customer.CustomerAlreadyExistsException;
import uol.compass.customer.exception.customer.CustomerNotFoundException;
import uol.compass.customer.repository.CustomerRepository;
import uol.compass.customer.service.impl.CustomerServiceImpl;
import uol.compass.customer.util.CustomerUtil;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Messaging Service")
@ContextConfiguration(classes= MapperConfiguration.class)
public class CustomerServiceTest {

    private CustomerService service;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void setUp() {
        service = new CustomerServiceImpl(this.customerRepository, this.fileUploadService, this.mapper);
    }

    @Test
    @DisplayName("Service create customer")
    public void testCreateCustomer() {
        final var customer = CustomerUtil.getCustomer();
        final var request = CustomerUtil.getRequest();

        when(this.customerRepository.existsByEmailIgnoreCaseOrCpf(anyString(), anyString())).thenReturn(false);
        when(this.fileUploadService.uploadBase64File(anyString(), anyString())).thenReturn(customer.getPhoto());
        when(this.customerRepository.save(any())).thenReturn(customer);

        final var response = service.createCustomer(request);

        Assertions.assertEquals(customer.getId(), response.getId());
        Assertions.assertEquals(customer.getCpf(), response.getCpf());
        Assertions.assertEquals(customer.getName(), response.getName());
        Assertions.assertEquals(customer.getGender(), response.getGender());
        Assertions.assertEquals(customer.getEmail(), response.getEmail());
        Assertions.assertEquals(customer.getPhoto(), response.getPhoto());
        Assertions.assertEquals(customer.getBirthDate(), response.getBirthDate());
        Assertions.assertEquals(customer.getPoints(), response.getPoints());
    }

    @Test
    @DisplayName("Service create customer already exists")
    public void testCreateCustomerAlreadyExists() {
        final var request = CustomerUtil.getRequest();

        when(this.customerRepository.existsByEmailIgnoreCaseOrCpf(anyString(), anyString())).thenThrow(CustomerAlreadyExistsException.class);
        Assertions.assertThrows(CustomerAlreadyExistsException.class, () -> service.createCustomer(request));
    }

    @Test
    @DisplayName("Service get customer")
    public void testGetCustomer() {
        final var customer = CustomerUtil.getCustomer();

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        final var response = service.getCustomer(customer.getId());

        Assertions.assertEquals(customer.getId(), response.getId());
        Assertions.assertEquals(customer.getCpf(), response.getCpf());
        Assertions.assertEquals(customer.getName(), response.getName());
        Assertions.assertEquals(customer.getGender(), response.getGender());
        Assertions.assertEquals(customer.getEmail(), response.getEmail());
        Assertions.assertEquals(customer.getPhoto(), response.getPhoto());
        Assertions.assertEquals(customer.getBirthDate(), response.getBirthDate());
        Assertions.assertEquals(customer.getPoints(), response.getPoints());
    }

    @Test
    @DisplayName("Service get customer not found")
    public void testGetCustomerNotFound() {
        final var customer = CustomerUtil.getCustomer();

        when(this.customerRepository.findById(anyLong())).thenThrow(CustomerNotFoundException.class);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.getCustomer(customer.getId()));
    }

    @Test
    @DisplayName("Service update customer")
    public void testUpdateCustomer() {
        final var customer = CustomerUtil.getUpdatedCustomer();
        final var request = CustomerUtil.getUpdateRequest();

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(this.customerRepository.existsByEmailOrCpf(anyString(), anyString(), anyLong())).thenReturn(false);
        when(this.fileUploadService.uploadBase64File(anyString(), anyString())).thenReturn(customer.getPhoto());
        when(this.customerRepository.save(any())).thenReturn(customer);

        final var response = service.updateCustomer(customer.getId(), request);

        Assertions.assertEquals(customer.getId(), response.getId());
        Assertions.assertEquals(customer.getCpf(), response.getCpf());
        Assertions.assertEquals(customer.getName(), response.getName());
        Assertions.assertEquals(customer.getGender(), response.getGender());
        Assertions.assertEquals(customer.getEmail(), response.getEmail());
        Assertions.assertEquals(customer.getPhoto(), response.getPhoto());
        Assertions.assertEquals(customer.getBirthDate(), response.getBirthDate());
        Assertions.assertEquals(customer.getPoints(), response.getPoints());
    }

    @Test
    @DisplayName("Service update customer not found")
    public void testUpdateCustomerNotFound() {
        final var customer = CustomerUtil.getUpdatedCustomer();
        final var request = CustomerUtil.getUpdateRequest();

        when(this.customerRepository.findById(anyLong())).thenThrow(CustomerNotFoundException.class);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.updateCustomer(customer.getId(), request));
    }

    @Test
    @DisplayName("Service update customer already exists")
    public void testUpdateCustomerAlreadyExists() {
        final var customer = CustomerUtil.getUpdatedCustomer();
        final var request = CustomerUtil.getUpdateRequest();

        when(this.customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(this.customerRepository.existsByEmailOrCpf(anyString(), anyString(), anyLong())).thenThrow(CustomerAlreadyExistsException.class);
        Assertions.assertThrows(CustomerAlreadyExistsException.class, () -> service.updateCustomer(customer.getId(), request));
    }

    @Test
    @DisplayName("Service delete customer")
    public void testDeleteCustomer() {
        final var customer = CustomerUtil.getCustomer();

        when(this.customerRepository.deleteCustomerById(anyLong())).thenReturn(1);
        Assertions.assertEquals(1, this.customerRepository.deleteCustomerById(customer.getId()));
    }

    @Test
    @DisplayName("Service delete customer not found")
    public void testDeleteCustomerNotFound() {
        final var customer = CustomerUtil.getCustomer();

        when(this.customerRepository.deleteCustomerById(anyLong())).thenReturn(0);
        Assertions.assertThrows(CustomerNotFoundException.class, () -> service.deleteCustomer(customer.getId()));
    }


}
