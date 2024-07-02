package uol.compass.customer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import uol.compass.customer.dto.request.PointsRequest;
import uol.compass.customer.repository.CustomerRepository;
import uol.compass.customer.service.MessagingService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMessagingServiceImpl implements MessagingService {

    private final ObjectMapper objectMapper;
    private final CustomerRepository repository;

    @RabbitListener(queues = {"${rabbit.queue.name}"})
    public void receivePoints(@Payload String message) {
        try {
            final var request = this.objectMapper.readValue(message, PointsRequest.class);
            this.repository.updatePointsById(request.getCustomerId(), request.getPoints());
        } catch (Exception exception) {
            log.error("Error parsing message: {}", message, exception);
        }
    }

}