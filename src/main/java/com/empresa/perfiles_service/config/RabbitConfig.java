package com.empresa.perfiles_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.queue.perfiles}")
    private String queueName;

    @Value("${rabbitmq.routingkey.creado}")
    private String routingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("empleados-exchange");
    }

    @Bean
    public Queue perfilesQueue() {
        return new Queue("perfiles-queue");
    }

    @Bean
    public Binding binding(Queue perfilesQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(perfilesQueue)
                .to(exchange)
                .with("empleado.creado");
    }
}