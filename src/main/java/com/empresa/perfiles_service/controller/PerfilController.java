package com.empresa.perfiles_service.controller;

import com.empresa.perfiles_service.dto.ApiResponse;
import com.empresa.perfiles_service.dto.PerfilResponseDTO;
import com.empresa.perfiles_service.dto.PerfilUpdateDTO;
import com.empresa.perfiles_service.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    private final PerfilService service;

    public PerfilController(PerfilService service) {
        this.service = service;
    }

    // 🔹 Obtener todos los perfiles
    @GetMapping
    public ResponseEntity<ApiResponse<List<PerfilResponseDTO>>> listarPerfiles() {

        List<PerfilResponseDTO> perfiles = service.listarPerfiles();

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Perfiles encontrados", perfiles)
        );
    }

    // 🔹 Obtener perfil por empleadoId
    @GetMapping("/{empleadoId}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> obtenerPerfil(@PathVariable String empleadoId) {

        PerfilResponseDTO perfil = service.obtenerPerfil(empleadoId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Perfil encontrado", perfil)
        );
    }

    // 🔹 Actualizar perfil
    @PutMapping("/{empleadoId}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> actualizarPerfil(
            @PathVariable String empleadoId,
            @Valid @RequestBody PerfilUpdateDTO dto) {

        PerfilResponseDTO perfilActualizado = service.actualizarPerfil(empleadoId, dto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Perfil actualizado", perfilActualizado)
        );
    }

    // 🔹 Desactivar perfil (Pruebas)
    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<ApiResponse<PerfilResponseDTO>> desactivarPerfil(
            @PathVariable String empleadoId) {

        PerfilResponseDTO perfil = service.desactivarPerfil(empleadoId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Perfil desactivado", perfil)
        );
    }
}