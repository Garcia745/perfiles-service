package com.empresa.perfiles_service.messaging;

import com.empresa.perfiles_service.dto.EmpleadoEventDTO;
import com.empresa.perfiles_service.service.PerfilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 🔹 Listener de eventos de empleados
 *
 * Escucha eventos desde RabbitMQ y ejecuta acciones en perfiles:
 *
 * - CREADO → crea perfil automáticamente
 * - ELIMINADO → desactiva perfil (soft delete)
 */
@Slf4j
@Component
public class PerfilEventListener {

    private final PerfilService perfilService;

    public PerfilEventListener(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * Este método se ejecuta cada vez que llega un mensaje a la cola.
     *
     * Spring convierte automáticamente el JSON → EmpleadoEventDTO
     */
    @RabbitListener(queues = "${rabbitmq.queue.perfiles}")
    public void procesarEventoEmpleado(EmpleadoEventDTO evento) {

        // 🔹 Validación básica para evitar errores
        if (evento.getEvento() == null || evento.getId() == null) {
            log.warn("Evento inválido recibido: {}", evento);
            return;
        }

        log.info("Evento recibido: {} - ID: {} - Nombre: {}",
                evento.getEvento(),
                evento.getId(),
                evento.getNombre());

        switch (evento.getEvento().toUpperCase()) {

            case "CREADO":
                perfilService.crearPerfilPorDefecto(
                        evento.getId(),
                        evento.getNombre(),
                        evento.getEmail()
                );
                break;

            case "ELIMINADO":
                perfilService.desactivarPerfil(evento.getId());
                break;

            default:
                log.warn("Evento no reconocido: {}", evento.getEvento());
        }
    }
}