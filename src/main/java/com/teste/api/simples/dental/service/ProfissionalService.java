package com.teste.api.simples.dental.service;

import com.teste.api.simples.dental.dtos.ProfissionalDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.entities.Profissional;
import com.teste.api.simples.dental.mapper.ProfissionalMapper;
import com.teste.api.simples.dental.repositories.ContatoRepository;
import com.teste.api.simples.dental.repositories.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    public List<ProfissionalDTO> buscarProfissionais(String q, List<String> fields) {
        List<Profissional> profissionais;

        if (q != null && !q.isEmpty()) {
            profissionais = profissionalRepository.findByNomeContainingOrCargoContainingOrContatos_NomeContaining(q, q, q);
        } else {
            profissionais = profissionalRepository.findAll();
        }

        return profissionais.stream()
                .map(profissional -> {
                    ProfissionalDTO dto = profissionalMapper.profissionalToProfissionalDTO(profissional);

                    if (fields != null && !fields.isEmpty()) {
                        return filtrarCampos(dto, fields);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Filtra os campos do DTO de acordo com a lista `fields`
     */
    private ProfissionalDTO filtrarCampos(ProfissionalDTO dto, List<String> fields) {
        ProfissionalDTO dtoFiltrado = new ProfissionalDTO();

        if (fields.contains("nome")) {
            dtoFiltrado.setNome(dto.getNome());
        }
        if (fields.contains("cargo")) {
            dtoFiltrado.setCargo(dto.getCargo());
        }
        if (fields.contains("nascimento")) {
            dtoFiltrado.setNascimento(dto.getNascimento());
        }
        if (fields.contains("contatos")) {
            dtoFiltrado.setContatos(dto.getContatos());
        }

        return dtoFiltrado;
    }


    public ProfissionalDTO obterProfissional(Long id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        return profissional.map(profissionalMapper::profissionalToProfissionalDTO).orElse(null); // Usando o MapStruct para mapear
    }

    public Long criarProfissional(Profissional profissional) {
        Profissional profissionalSalvo = profissionalRepository.save(profissional);
        return profissionalSalvo.getId();
    }

    public void atualizarProfissional(Long id, Profissional profissional) {
        Optional<Profissional> profissionalExistente = profissionalRepository.findById(id);
        if (profissionalExistente.isPresent()) {
            Profissional p = profissionalExistente.get();
            p.setNome(profissional.getNome());
            p.setCargo(profissional.getCargo());
            p.setNascimento(profissional.getNascimento());
            profissionalRepository.save(p);
        }
    }

    public void excluirProfissional(Long id) {
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        if (profissional.isPresent()) {
            profissionalRepository.delete(profissional.get());
        }
    }

    public void adicionarContato(Long profissionalId, Contato contato) {
        Optional<Profissional> profissional = profissionalRepository.findById(profissionalId);
        if (profissional.isPresent()) {
            contato.setProfissional(profissional.get());
            contatoRepository.save(contato);
        }
    }
}
