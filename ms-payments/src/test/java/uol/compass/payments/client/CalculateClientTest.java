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
import uol.compass.payments.util.CalculateUtil;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Calculate client")
@ContextConfiguration(classes= {FeignConfiguration.class, ObjectMapperConfiguration.class})
public class CalculateClientTest {

    @Mock
    private CalculateClient client;

    @Test
    @DisplayName("Client test calculate success")
    public void testCalculate() {
        when(this.client.calculate(any())).thenReturn(CalculateUtil.getResponse());

        final var response = this.client.calculate(CalculateUtil.getRequest());
        Assertions.assertEquals(CalculateUtil.getResponse().getTotal(), response.getTotal());
    }

}