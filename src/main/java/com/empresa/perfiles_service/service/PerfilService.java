package com.empresa.perfiles_service.service;

import com.empresa.perfiles_service.dto.PerfilResponseDTO;
import com.empresa.perfiles_service.dto.PerfilUpdateDTO;
import com.empresa.perfiles_service.exception.PerfilNotFoundException;
import com.empresa.perfiles_service.mapper.PerfilMapper;
import com.empresa.perfiles_service.model.Perfil;
import com.empresa.perfiles_service.repository.PerfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PerfilService {

    private final PerfilRepository repository;
    private final PerfilMapper mapper;

    public PerfilService(PerfilRepository repository, PerfilMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ✅ Crear perfil automáticamente
    public PerfilResponseDTO crearPerfilPorDefecto(String empleadoId, String nombre, String email) {

        return repository.findByEmpleadoId(empleadoId)
                .map(perfil -> {
                    log.warn("Perfil ya existe: {}", empleadoId);
                    return mapper.toResponseDTO(perfil);
                })
                .orElseGet(() -> {
                    Perfil perfil = Perfil.builder()
                            .id(UUID.randomUUID().toString())
                            .empleadoId(empleadoId)
                            .nombre(nombre)
                            .email(email)
                            .telefono("")
                            .direccion("")
                            .ciudad("")
                            .biografia("")
                            .activo(true)
                            .fechaCreacion(LocalDateTime.now())
                            .build();

                    log.info("Creando perfil para empleado: {}", empleadoId);
                    return mapper.toResponseDTO(repository.save(perfil));
                });
    }

    // ✅ Soft delete
    public PerfilResponseDTO desactivarPerfil(String empleadoId) {

        Perfil perfil = repository.findByEmpleadoId(empleadoId)
                .orElseThrow(() -> new PerfilNotFoundException("Perfil no encontrado"));

        if (!perfil.isActivo()) {
            log.warn("Perfil ya desactivado: {}", empleadoId);
            return mapper.toResponseDTO(perfil);
        }

        perfil.setActivo(false);
        Perfil actualizado = repository.save(perfil);

        log.info("Perfil desactivado: {}", empleadoId);
        return mapper.toResponseDTO(actualizado);
    }

    // ✅ Listar
    public List<PerfilResponseDTO> listarPerfiles() {
        return mapper.toResponseDTOList(repository.findAll());
    }

    // ✅ Obtener uno
    public PerfilResponseDTO obtenerPerfil(String empleadoId) {
        Perfil perfil = repository.findByEmpleadoId(empleadoId)
                .orElseThrow(() -> new PerfilNotFoundException("Perfil no encontrado"));

        return mapper.toResponseDTO(perfil);
    }

    // ✅ Actualizar
    public PerfilResponseDTO actualizarPerfil(String empleadoId, PerfilUpdateDTO dto) {

        Perfil perfil = repository.findByEmpleadoId(empleadoId)
                .orElseThrow(() -> new PerfilNotFoundException("Perfil no encontrado"));

        mapper.actualizarPerfilDesdeDTO(dto, perfil);

        Perfil actualizado = repository.save(perfil);

        log.info("Perfil actualizado: {}", empleadoId);
        return mapper.toResponseDTO(actualizado);
    }
}