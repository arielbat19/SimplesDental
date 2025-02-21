package com.teste.api.simples.dental.service;

import com.teste.api.simples.dental.dtos.ProfissionalDTO;
import com.teste.api.simples.dental.entities.Contato;
import com.teste.api.simples.dental.entities.Profissional;
import com.teste.api.simples.dental.enums.Cargo;
import com.teste.api.simples.dental.mapper.ProfissionalMapper;
import com.teste.api.simples.dental.repositories.ContatoRepository;
import com.teste.api.simples.dental.repositories.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProfissionalServiceTest {

    @InjectMocks
    private ProfissionalService profissionalService;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Mock
    private ProfissionalMapper profissionalMapper;

    @Mock
    private ContatoRepository contatoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarProfissionaisComFiltro() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Profissional Teste");
        profissional.setCargo(Cargo.DESENVOLVEDOR);
        profissional.setNascimento(new Date());

        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Profissional Teste");
        profissionalDTO.setCargo(Cargo.DESENVOLVEDOR);
        profissionalDTO.setNascimento(profissional.getNascimento());

        when(profissionalRepository.findByNomeContainingOrCargoContainingOrContatos_NomeContaining("Teste", "Teste", "Teste"))
                .thenReturn(List.of(profissional));

        when(profissionalMapper.profissionalToProfissionalDTO(profissional)).thenReturn(profissionalDTO);

        List<ProfissionalDTO> profissionais = profissionalService.buscarProfissionais("Teste", null);

        assertNotNull(profissionais);
        assertFalse(profissionais.isEmpty());
        assertEquals(1, profissionais.size());
        assertNotNull(profissionais.get(0));
        assertEquals("Profissional Teste", profissionais.get(0).getNome());
        assertEquals(Cargo.DESENVOLVEDOR, profissionais.get(0).getCargo());
        assertNotNull(profissionais.get(0).getNascimento());

        verify(profissionalRepository, times(1)).findByNomeContainingOrCargoContainingOrContatos_NomeContaining("Teste", "Teste", "Teste");
        verify(profissionalMapper, times(1)).profissionalToProfissionalDTO(profissional);
    }

    @Test
    void testBuscarProfissionaisSemFiltro() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Profissional Teste");
        profissional.setCargo(Cargo.DESENVOLVEDOR);
        profissional.setNascimento(new Date());
        profissional.setCreatedDate(new Date());

        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Profissional Teste");
        profissionalDTO.setCargo(Cargo.DESENVOLVEDOR);
        profissionalDTO.setNascimento(profissional.getNascimento());

        when(profissionalRepository.findAll()).thenReturn(List.of(profissional));

        when(profissionalMapper.profissionalToProfissionalDTO(profissional)).thenReturn(profissionalDTO);

        List<ProfissionalDTO> profissionais = profissionalService.buscarProfissionais(null, null);

        assertNotNull(profissionais);
        assertEquals(1, profissionais.size());
        assertEquals("Profissional Teste", profissionais.get(0).getNome());
        assertEquals(Cargo.DESENVOLVEDOR, profissionais.get(0).getCargo());
        assertNotNull(profissionais.get(0).getNascimento());

        verify(profissionalRepository, times(1)).findAll();
        verify(profissionalMapper, times(1)).profissionalToProfissionalDTO(profissional);
    }


    @Test
    void testObterProfissional() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Profissional Teste");
        profissional.setCargo(Cargo.DESENVOLVEDOR);
        profissional.setNascimento(new Date());

        ProfissionalDTO profissionalDTO = new ProfissionalDTO();
        profissionalDTO.setNome("Profissional Teste");
        profissionalDTO.setCargo(Cargo.DESENVOLVEDOR);
        profissionalDTO.setNascimento(profissional.getNascimento());

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));

        when(profissionalMapper.profissionalToProfissionalDTO(profissional)).thenReturn(profissionalDTO);

        ProfissionalDTO resultado = profissionalService.obterProfissional(1L);

        assertNotNull(resultado);
        assertEquals("Profissional Teste", resultado.getNome());
        assertEquals(Cargo.DESENVOLVEDOR, resultado.getCargo());
        assertNotNull(resultado.getNascimento());

        verify(profissionalRepository, times(1)).findById(1L);
        verify(profissionalMapper, times(1)).profissionalToProfissionalDTO(profissional);
    }


    @Test
    void testObterProfissionalNaoEncontrado() {
        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());

        ProfissionalDTO profissionalDTO = profissionalService.obterProfissional(1L);

        assertNull(profissionalDTO);

        verify(profissionalRepository, times(1)).findById(1L);
    }

    @Test
    void testCriarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Novo Profissional");
        profissional.setId(1L);

        when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissional);

        Long id = profissionalService.criarProfissional(profissional);

        assertNotNull(id);
        assertEquals(profissional.getId(), id);

        verify(profissionalRepository, times(1)).save(profissional);
    }


    @Test
    void testAtualizarProfissional() {
        Profissional profissionalExistente = new Profissional();
        profissionalExistente.setNome("Profissional Existente");

        Profissional profissionalAtualizado = new Profissional();
        profissionalAtualizado.setNome("Profissional Atualizado");

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissionalExistente));
        when(profissionalRepository.save(any(Profissional.class))).thenReturn(profissionalExistente);

        profissionalService.atualizarProfissional(1L, profissionalAtualizado);

        assertEquals("Profissional Atualizado", profissionalExistente.getNome());

        verify(profissionalRepository, times(1)).findById(1L);
        verify(profissionalRepository, times(1)).save(profissionalExistente);
    }

    @Test
    void testExcluirProfissional() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));

        profissionalService.excluirProfissional(1L);

        verify(profissionalRepository, times(1)).delete(profissional);
    }

    @Test
    void testAdicionarContato() {
        Profissional profissional = new Profissional();
        profissional.setId(1L);

        Contato contato = new Contato();
        contato.setNome("Contato Teste");

        when(profissionalRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        profissionalService.adicionarContato(1L, contato);

        verify(profissionalRepository, times(1)).findById(1L);
        verify(contatoRepository, times(1)).save(contato);
    }

    @Test
    void testAdicionarContatoProfissionalNaoEncontrado() {
        Contato contato = new Contato();
        contato.setNome("Contato Teste");

        when(profissionalRepository.findById(1L)).thenReturn(Optional.empty());

        profissionalService.adicionarContato(1L, contato);

        verify(profissionalRepository, times(1)).findById(1L);
        verify(contatoRepository, times(0)).save(any(Contato.class));
    }
}
