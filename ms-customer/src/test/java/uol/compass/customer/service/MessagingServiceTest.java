package uol.compass.customer.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.customer.configuration.ObjectMapperConfiguration;
import uol.compass.customer.dto.request.PointsRequest;
import uol.compass.customer.repository.CustomerRepository;
import uol.compass.customer.service.impl.RabbitMessagingServiceImpl;
import uol.compass.customer.util.PointsUtil;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Messaging Service")
@ContextConfiguration(classes= ObjectMapperConfiguration.class)
public class MessagingServiceTest {

    private MessagingService service;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void setup() {
        this.service = new RabbitMessagingServiceImpl(this.mapper, this.repository);
    }

    @Test
    @DisplayName("Service receive points")
    void testReceivePoints() throws JsonProcessingException {
        final var message = this.mapper.writeValueAsString(PointsUtil.getRequest());
        this.service.receivePoints(message);
    }

    @Test
    @DisplayName("Service receive points with invalid message")
    void testReceivePointsInvalidMessage() throws JsonProcessingException {
        final var spy = spy(this.mapper);

        doThrow(JsonParseException.class).when(spy).readValue(anyString(), eq(PointsRequest.class));
        this.service.receivePoints(anyString());
    }

}