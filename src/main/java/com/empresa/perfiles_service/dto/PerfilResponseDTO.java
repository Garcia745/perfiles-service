package com.empresa.perfiles_service.dto;

import lombok.Data;

@Data
public class PerfilResponseDTO {

    private String empleadoId;
    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private String biografia;
}