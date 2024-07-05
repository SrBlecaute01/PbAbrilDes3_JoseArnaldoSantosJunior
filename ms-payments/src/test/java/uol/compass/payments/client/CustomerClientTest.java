package uol.compass.payments.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.payments.configuration.FeignConfiguration;
import uol.compass.payments.configuration.ObjectMapperConfiguration;
import uol.compass.payments.util.CustomerUtil;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Customer client")
@ContextConfiguration(classes= {FeignConfiguration.class, ObjectMapperConfiguration.class})
public class CustomerClientTest {

    @Mock
    private CustomerClient client;

    @Test
    @DisplayName("Client test get customer success")
    void testGetCustomerSuccess() {
        when(this.client.getCustomer(anyLong())).thenReturn(CustomerUtil.getResponse());

        final var response = this.client.getCustomer(1L);
        Assertions.assertEquals(CustomerUtil.getResponse().getCpf(), response.getCpf());
    }

}
