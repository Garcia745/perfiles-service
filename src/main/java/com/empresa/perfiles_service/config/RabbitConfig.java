package com.empresa.perfiles_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el servicio de perfiles.
 * Aquí se definen:
 * - El exchange: el "punto de encuentro" de los mensajes.
 * - La cola: donde se reciben los mensajes.
 * - La conexión entre ellos mediante una routing key.
 */
@Configuration
public class RabbitConfig {

    // Nombre del exchange, la cola y la routing key (pueden configurarse desde properties)
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.queue.perfiles}")
    private String queueName;

    @Value("${rabbitmq.routingkey.creado}")
    private String routingKey;

    // Crea el exchange (canal principal por donde se envían los mensajes)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("empleados-exchange");
    }

    // Crea la cola donde el servicio escuchará los mensajes
    @Bean
    public Queue perfilesQueue() {
        return new Queue("perfiles-queue");
    }

    // Conecta la cola con el exchange usando la routing key
    // Esto asegura que los mensajes correctos lleguen a la cola
    @Bean
    public Binding binding(Queue perfilesQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(perfilesQueue)
                .to(exchange)
                .with("empleado.creado");
    }
}