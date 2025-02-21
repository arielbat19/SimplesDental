package com.teste.api.simples.dental.controller;

import com.teste.api.simples.dental.dtos.ProfissionalDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.entities.Profissional;
import com.teste.api.simples.dental.service.ProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProfissionalControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProfissionalController profissionalController;

    @Mock
    private ProfissionalService profissionalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profissionalController).build();
    }

    @Test
    void testBuscarProfissionais() throws Exception {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Profissional Teste");

        when(profissionalService.buscarProfissionais("Teste", List.of("nome"))).thenReturn(List.of(profissionalDTO));

        mockMvc.perform(get("/profissionais?q=Teste&fields=nome")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Profissional Teste"));

        verify(profissionalService, times(1)).buscarProfissionais("Teste", List.of("nome"));
    }

    @Test
    void testObterProfissional() throws Exception {
        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Profissional Teste");

        when(profissionalService.obterProfissional(1L)).thenReturn(profissionalDTO);

        mockMvc.perform(get("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Profissional Teste"));

        verify(profissionalService, times(1)).obterProfissional(1L);
    }

    @Test
    void testObterProfissionalNaoEncontrado() throws Exception {
        when(profissionalService.obterProfissional(1L)).thenReturn(null);

        mockMvc.perform(get("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(profissionalService, times(1)).obterProfissional(1L);
    }

    @Test
    void testCriarProfissional() throws Exception {
        when(profissionalService.criarProfissional(any(Profissional.class))).thenReturn(1L);


        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Novo Profissional\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Profissional com id 1 cadastrado com sucesso"));

        verify(profissionalService, times(1)).criarProfissional(any(Profissional.class));
    }

    @Test
    void testAtualizarProfissional() throws Exception {
        mockMvc.perform(put("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Profissional Atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Profissional atualizado com sucesso"));

        verify(profissionalService, times(1)).atualizarProfissional(eq(1L), any(Profissional.class));
    }

    @Test
    void testExcluirProfissional() throws Exception {
        mockMvc.perform(delete("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Profissional excluído com sucesso"));

        verify(profissionalService, times(1)).excluirProfissional(1L);
    }

    @Test
    void testAdicionarContato() throws Exception {
        mockMvc.perform(post("/profissionais/1/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Contato Teste\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Contato adicionado ao profissional com id 1"));

        verify(profissionalService, times(1)).adicionarContato(eq(1L), any(Contato.class));
    }

}
