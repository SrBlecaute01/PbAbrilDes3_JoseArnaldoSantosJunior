package uol.compass.payments.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Value("${rabbit.queue.name}")
    private String queueName;

    @Value("${rabbit.exchange.name}")
    private String exchangeName;

    @Value("${rabbit.routing.key}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(this.queueName, false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(this.exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey).noargs();
    }

}