package com.empresa.perfiles_service.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

/**
 * Configuración de RabbitMQ para el microservicio de perfiles.
 *
 * Este archivo define:
 * - Exchange: punto central donde llegan los eventos
 * - Queue: cola donde este microservicio escucha
 * - Bindings: reglas que conectan exchange con la cola mediante routing keys
 *
 * En este caso el servicio escucha eventos de:
 * - empleado.creado → para crear perfil automáticamente
 * - empleado.eliminado → para desactivar perfil (soft delete)
 */
@Configuration
public class RabbitConfig {

    // 🔹 Valores externos definidos en application.properties
    @Value("${rabbitmq.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.queue.perfiles}")
    private String queueName;

    @Value("${rabbitmq.routingkey.creado}")
    private String routingKeyCreado;

    @Value("${rabbitmq.routingkey.eliminado}")
    private String routingKeyEliminado;

    /**
     * 🔹 Exchange tipo TOPIC
     * Permite enrutar mensajes usando patrones como:
     * - empleado.creado
     * - empleado.eliminado
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    /**
     * 🔹 Cola donde el microservicio recibe eventos
     *
     * Se configura como durable (true), lo que significa que:
     * ✔ La cola no se pierde si RabbitMQ se reinicia
     */
    @Bean
    public Queue perfilesQueue() {
        return new Queue(queueName, true);
    }

    /**
     * 🔹 Binding para evento: empleado.creado
     *
     * Este binding indica que:
     * 👉 Todos los mensajes enviados al exchange con routing key "empleado.creado"
     * 👉 serán enviados a la cola "perfiles-queue"
     */
    @Bean
    public Binding bindingEmpleadoCreado(Queue perfilesQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(perfilesQueue)
                .to(exchange)
                .with(routingKeyCreado);
    }

    /**
     * 🔹 Binding para evento: empleado.eliminado
     *
     * Este binding indica que:
     * 👉 Todos los mensajes enviados al exchange con routing key "empleado.eliminado"
     * 👉 también serán enviados a la cola "perfiles-queue"
     */
    @Bean
    public Binding bindingEmpleadoEliminado(Queue perfilesQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(perfilesQueue)
                .to(exchange)
                .with(routingKeyEliminado);
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}