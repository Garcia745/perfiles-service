package com.empresa.perfiles_service.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "perfiles")
@Builder
public class Perfil {
    @Id
    private String id;
    private String empleadoId;
    private String nombre;
    private boolean activo;
    private String email;
    private String telefono = "";
    private String direccion = "";
    private String ciudad = "";
    private String biografia = "";
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
