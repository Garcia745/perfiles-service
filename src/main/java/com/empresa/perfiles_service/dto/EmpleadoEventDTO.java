package com.empresa.perfiles_service.dto;

import lombok.Data;

/**
 * DTO que representa el evento enviado desde el microservicio de empleados.
 * Spring convertirá automáticamente el JSON recibido a este objeto.
 */
@Data
public class EmpleadoEventDTO {

    private String evento; // CREADO o ELIMINADO
    private String id;
    private String nombre;
    private String email;
}