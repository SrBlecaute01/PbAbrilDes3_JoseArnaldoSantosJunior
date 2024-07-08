package uol.compass.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uol.compass.payments.configuration.ObjectMapperConfiguration;
import uol.compass.payments.service.impl.RabbitMessagingServiceImpl;
import uol.compass.payments.util.PointsUtil;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Messaging Service")
@ContextConfiguration(classes= ObjectMapperConfiguration.class)
public class MessagingServiceTest {

    private MessagingService service;

    @Mock
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        this.service = new RabbitMessagingServiceImpl(this.template, this.mapper);

        ReflectionTestUtils.setField(this.service, "exchangeName", "exchange");
        ReflectionTestUtils.setField(this.service, "routingKey", "key");
    }

    @Test
    @DisplayName("Service send points message")
    void testSendPointsMessage() throws JsonProcessingException {
        final var spy = spy(this.mapper);

        when(spy.writeValueAsString(any())).thenReturn("json");
        this.service.sendPointsMessage(PointsUtil.getRequest());

        reset(spy);
    }

    @Test
    @DisplayName("Service send points invalid json")
    void testSendPointsInvalidJson() throws JsonProcessingException {
        final var spy = spy(this.mapper);

        when(spy.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        this.service.sendPointsMessage(PointsUtil.getRequest());

        reset(spy);
    }

    @Test
    @DisplayName("Service send points amq exception")
    void testSendPointsAmqException() throws JsonProcessingException {
        final var spy = spy(this.mapper);

        when(spy.writeValueAsString(any())).thenReturn("json");
        doThrow(AmqpException.class).when(template).convertAndSend(anyString(), anyString(), anyString());
        this.service.sendPointsMessage(PointsUtil.getRequest());

        reset(spy);
    }

}
