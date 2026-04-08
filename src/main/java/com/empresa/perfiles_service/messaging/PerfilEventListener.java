package com.empresa.perfiles_service.messaging;

import com.empresa.perfiles_service.service.PerfilService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

/**
 * Este componente escucha los eventos relacionados con empleados que se crean
 * y automáticamente genera un perfil por defecto para cada nuevo empleado.
 */
@Component
public class PerfilEventListener {

    private final PerfilService perfilService;

    // Se inyecta el servicio que maneja la lógica de creación de perfiles
    public PerfilEventListener(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * Método que se ejecuta cada vez que llega un mensaje a la cola "perfiles-queue".
     * @param mensaje Información del empleado en formato JSON
     */
    @RabbitListener(queues = "perfiles-queue")
    public void recibirEmpleadoCreado(String mensaje) {

        // Convertir el mensaje JSON a un objeto para extraer los datos
        JSONObject json = new JSONObject(mensaje);

        String id = json.getString("id");
        String nombre = json.getString("nombre");
        String email = json.getString("email");

        // Crear el perfil automáticamente usando los datos recibidos
        perfilService.crearPerfilPorDefecto(id, nombre, email);
    }
}