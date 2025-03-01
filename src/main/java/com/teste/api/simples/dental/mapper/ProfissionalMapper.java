package com.teste.api.simples.dental.mapper;

import com.teste.api.simples.dental.dtos.ProfissionalDTO;
import com.teste.api.simples.dental.entities.Profissional;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProfissionalMapper {

    ProfissionalDTO profissionalToProfissionalDTO(Profissional profissional);

    Profissional profissionalDTOToProfissional(ProfissionalDTO profissionalDTO);
}

