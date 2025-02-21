package com.teste.api.simples.dental.repositories;

import com.teste.api.simples.dental.entities.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByNomeContainingOrContatoContaining(String nome, String contato);
}
