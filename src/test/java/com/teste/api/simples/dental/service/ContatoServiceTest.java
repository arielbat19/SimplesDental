package com.teste.api.simples.dental.service;

import com.teste.api.simples.dental.dtos.ContatoDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.entities.Profissional;
import com.teste.api.simples.dental.repositories.ContatoRepository;
import com.teste.api.simples.dental.repositories.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ContatoService contatoService;

    private Contato contato;
    private Profissional profissional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Profissional Teste");
        contato = new Contato();
        contato.setId(1L);
        contato.setNome("Contato Teste");
        contato.setContato("123456789");
    }

    @Test
    void testBuscarContatosComQuery() {
        when(contatoRepository.findByNomeContainingOrContatoContaining("Teste", "Teste"))
                .thenReturn(List.of(contato));
        var contatos = contatoService.buscarContatos("Teste", List.of("nome", "contato"));
        assertNotNull(contatos);
        assertEquals(1, contatos.size());
        assertEquals("Contato Teste", contatos.get(0).getNome());
    }

    @Test
    void testBuscarContatosSemQuery() {
        when(contatoRepository.findAll()).thenReturn(List.of(contato));
        var contatos = contatoService.buscarContatos(null, List.of());
        assertNotNull(contatos);
        assertEquals(1, contatos.size());
    }

    @Test
    void testObterContato() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        ContatoDTO contatoObtido = contatoService.obterContato(1L);
        assertNotNull(contatoObtido);
        assertEquals("Contato Teste", contatoObtido.getNome());
    }

    @Test
    void testCriarContato() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(contatoRepository.save(contato)).thenReturn(contato);
        Long idCriado = contatoService.criarContato(contato, 1L);
        assertNotNull(idCriado);
        assertEquals(1L, idCriado);
    }

    @Test
    void testCriarContatoProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            contatoService.criarContato(contato, 1L);
        });
        assertEquals("Profissional não encontrado", thrown.getMessage());
    }

    @Test
    void testAtualizarContato() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        Contato novoContato = new Contato();
        novoContato.setNome("Novo Contato");
        novoContato.setContato("987654321");
        contatoService.atualizarContato(1L, novoContato);
        verify(contatoRepository).save(any(Contato.class));
    }

    @Test
    void testExcluirContato() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        contatoService.excluirContato(1L);
        verify(contatoRepository).delete(contato);
    }
}
