package com.teste.api.simples.dental.controller;

import com.teste.api.simples.dental.dtos.ProfissionalDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.entities.Profissional;
import com.teste.api.simples.dental.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
@Tag(name = "Profissionais", description = "Gerenciamento de profissionais e seus contatos")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Operation(summary = "Busca todos os profissionais", description = "Retorna uma lista de profissionais cadastrados, podendo filtrar por nome ou campos específicos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de profissionais retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @GetMapping
    public ResponseEntity<List<ProfissionalDTO>> buscarProfissionais(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<ProfissionalDTO> profissionais = profissionalService.buscarProfissionais(q, fields);
        return ResponseEntity.ok(profissionais);
    }

    @Operation(summary = "Obtém um profissional por ID", description = "Retorna os detalhes de um profissional específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional encontrado"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDTO> obterProfissional(@PathVariable Long id) {
        ProfissionalDTO profissional = profissionalService.obterProfissional(id);
        if (profissional != null) {
            return ResponseEntity.ok(profissional);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cria um novo profissional", description = "Cadastra um novo profissional no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profissional cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<String> criarProfissional(@RequestBody Profissional profissional) {
        Long id = profissionalService.criarProfissional(profissional);
        return ResponseEntity.status(201).body("Profissional com id " + id + " cadastrado com sucesso");
    }

    @Operation(summary = "Atualiza um profissional", description = "Modifica as informações de um profissional existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProfissional(@PathVariable Long id, @RequestBody Profissional profissional) {
        profissionalService.atualizarProfissional(id, profissional);
        return ResponseEntity.ok("Profissional atualizado com sucesso");
    }

    @Operation(summary = "Exclui um profissional", description = "Remove um profissional com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profissional excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProfissional(@PathVariable Long id) {
        profissionalService.excluirProfissional(id);
        return ResponseEntity.ok("Profissional excluído com sucesso");
    }

    @Operation(summary = "Adiciona um contato a um profissional", description = "Associa um contato a um profissional específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contato adicionado ao profissional com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    })
    @PostMapping("/{profissionalId}/contatos")
    public ResponseEntity<String> adicionarContato(@PathVariable Long profissionalId, @RequestBody Contato contato) {
        profissionalService.adicionarContato(profissionalId, contato);
        return ResponseEntity.status(201).body("Contato adicionado ao profissional com id " + profissionalId);
    }
}
