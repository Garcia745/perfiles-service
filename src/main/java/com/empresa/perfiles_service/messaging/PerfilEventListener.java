package com.empresa.perfiles_service.messaging;

import com.empresa.perfiles_service.service.PerfilService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

@Component
public class PerfilEventListener {

    private final PerfilService perfilService;

    public PerfilEventListener(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @RabbitListener(queues = "perfiles-queue")
    public void recibirEmpleadoCreado(String mensaje) {

        System.out.println("📩 Evento recibido: " + mensaje);

        JSONObject json = new JSONObject(mensaje);

        String id = json.getString("id");
        String nombre = json.getString("nombre");
        String email = json.getString("email");

        perfilService.crearPerfilPorDefecto(id, nombre, email);

        System.out.println("✅ Perfil creado automáticamente para: " + nombre);
    }
}