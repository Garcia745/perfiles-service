package com.empresa.perfiles_service.mapper;

import com.empresa.perfiles_service.dto.PerfilResponseDTO;
import com.empresa.perfiles_service.dto.PerfilUpdateDTO;
import com.empresa.perfiles_service.model.Perfil;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PerfilMapper {

    PerfilResponseDTO toResponseDTO(Perfil perfil);

    List<PerfilResponseDTO> toResponseDTOList(List<Perfil> perfiles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarPerfilDesdeDTO(PerfilUpdateDTO dto, @MappingTarget Perfil perfil);
}