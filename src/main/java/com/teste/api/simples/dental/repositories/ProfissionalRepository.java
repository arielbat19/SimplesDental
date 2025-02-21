package com.teste.api.simples.dental.repositories;

import com.teste.api.simples.dental.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    List<Profissional> findByNomeContainingOrCargoContainingOrContatos_NomeContaining(String nome, String cargo, String contato);
}
