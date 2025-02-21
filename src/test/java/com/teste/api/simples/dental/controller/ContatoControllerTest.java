package com.teste.api.simples.dental.controller;

import com.teste.api.simples.dental.dtos.ContatoDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.service.ContatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoControllerTest {

    @Mock
    private ContatoService contatoService;

    @InjectMocks
    private ContatoController contatoController;

    private Contato contato;
    private ContatoDTO contatoDTO;

    @BeforeEach
    void setUp() {
        contato = new Contato();
        contato.setId(1L);
        contato.setNome("João Silva");

        contatoDTO = new ContatoDTO();
        contatoDTO.setNome("João Silva");
        contatoDTO.setContato("Teste");
    }

    @Test
    void testBuscarContatos() {
        List<ContatoDTO> contatosDTO = Arrays.asList(contatoDTO);
        when(contatoService.buscarContatos(null, null)).thenReturn(contatosDTO);

        ResponseEntity<List<ContatoDTO>> response = contatoController.buscarContatos(null, null);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
    }

    @Test
    void testObterContatoExistente() {
        when(contatoService.obterContato(1L)).thenReturn(contatoDTO);

        ResponseEntity<ContatoDTO> response = contatoController.obterContato(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
    }

    @Test
    void testObterContatoNaoEncontrado() {
        when(contatoService.obterContato(2L)).thenReturn(null);

        ResponseEntity<ContatoDTO> response = contatoController.obterContato(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testCriarContato() {
        when(contatoService.criarContato(any(Contato.class), eq(1L))).thenReturn(1L);

        ResponseEntity<String> response = contatoController.criarContato(contato, 1L);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Contato com id 1 cadastrado com sucesso", response.getBody());
    }

    @Test
    void testAtualizarContato() {
        doNothing().when(contatoService).atualizarContato(eq(1L), any(Contato.class));

        ResponseEntity<String> response = contatoController.atualizarContato(1L, contato);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contato atualizado com sucesso", response.getBody());
    }

    @Test
    void testExcluirContato() {
        doNothing().when(contatoService).excluirContato(1L);

        ResponseEntity<String> response = contatoController.excluirContato(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contato excluído com sucesso", response.getBody());
    }
}
