package com.teste.api.simples.dental.controller;

import com.teste.api.simples.dental.dtos.ContatoDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
@Tag(name = "Contatos", description = "Gerenciamento de contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @Operation(summary = "Busca todos os contatos", description = "Retorna uma lista de contatos filtrados por nome ou campos específicos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de contatos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @GetMapping
    public ResponseEntity<List<ContatoDTO>> buscarContatos(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> fields) {
        List<ContatoDTO> contatos = contatoService.buscarContatos(q, fields);
        return ResponseEntity.ok(contatos);
    }

    @Operation(summary = "Obtém um contato por ID", description = "Retorna um contato específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contato encontrado"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> obterContato(@PathVariable Long id) {
        ContatoDTO contato = contatoService.obterContato(id);
        if (contato != null) {
            return ResponseEntity.ok(contato);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Cria um novo contato", description = "Cadastra um novo contato associado a um profissional.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<String> criarContato(@RequestBody Contato contato, @RequestParam Long profissionalId) {
        Long id = contatoService.criarContato(contato, profissionalId);
        return ResponseEntity.status(201).body("Contato com id " + id + " cadastrado com sucesso");
    }

    @Operation(summary = "Atualiza um contato", description = "Modifica as informações de um contato existente pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarContato(@PathVariable Long id, @RequestBody Contato contato) {
        contatoService.atualizarContato(id, contato);
        return ResponseEntity.ok("Contato atualizado com sucesso");
    }

    @Operation(summary = "Exclui um contato", description = "Remove um contato com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contato excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirContato(@PathVariable Long id) {
        contatoService.excluirContato(id);
        return ResponseEntity.ok("Contato excluído com sucesso");
    }
}
