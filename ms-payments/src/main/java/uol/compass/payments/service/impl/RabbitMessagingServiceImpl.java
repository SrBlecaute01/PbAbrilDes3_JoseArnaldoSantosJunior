package uol.compass.payments.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uol.compass.payments.dto.request.PointsRequest;
import uol.compass.payments.service.MessagingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMessagingServiceImpl implements MessagingService {

    private final RabbitTemplate template;
    private final ObjectMapper mapper;

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Override
    public void sendPointsMessage(PointsRequest request) {
        try {
            final var json  = this.mapper.writeValueAsString(request);
            this.template.convertAndSend(this.exchangeName, this.routingKey, json);
        } catch (JsonProcessingException exception) {
            log.error("Error while converting object to json", exception);
        } catch (Exception exception) {
            log.error("Error while sending message", exception);
        }
    }

}