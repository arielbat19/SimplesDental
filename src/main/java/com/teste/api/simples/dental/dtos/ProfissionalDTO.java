package com.teste.api.simples.dental.dtos;

import com.teste.api.simples.dental.enums.Cargo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProfissionalDTO {
    private String nome;
    private Cargo cargo;
    private Date nascimento;
    private List<ContatoDTO> contatos;
}
