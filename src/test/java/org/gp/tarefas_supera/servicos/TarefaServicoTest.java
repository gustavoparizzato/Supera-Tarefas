package org.gp.tarefas_supera.servicos;

import jakarta.persistence.EntityNotFoundException;
import org.gp.tarefas_supera.dto.TarefaDTO;
import org.gp.tarefas_supera.entidades.Tarefa;
import org.gp.tarefas_supera.repositorios.TarefaRepositorio;
import org.gp.tarefas_supera.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaServicoTest {

    @Mock
    private TarefaRepositorio tarefaRepositorio;

    @InjectMocks
    private TarefaServico tarefaServico;

    private Tarefa tarefa;
    private TarefaDTO tarefaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa Teste");
        tarefa.setDescricao("Descrição Teste");

        tarefaDTO = new TarefaDTO(tarefa);
    }

    @Test
    void buscarTodas() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(tarefaRepositorio.buscarTodas(anyLong(), anyString(), anyString(), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(tarefaDTO)));

        Page<TarefaDTO> result = tarefaServico.buscarTodas(1L, "titulo", "item", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(tarefaRepositorio, times(1)).buscarTodas(anyLong(), anyString(), anyString(), eq(pageable));

    }

    @Test
    void inserir() {
        when(tarefaRepositorio.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaDTO resultado = tarefaServico.inserir(tarefaDTO);

        assertNotNull(resultado);
        assertEquals(tarefaDTO.getTitulo(), resultado.getTitulo());
        verify(tarefaRepositorio, times(1)).save(any(Tarefa.class));
    }

    @Test
    void atualizar() {
        when(tarefaRepositorio.getReferenceById(anyLong())).thenReturn(tarefa);
        when(tarefaRepositorio.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaDTO resultado = tarefaServico.atualizar(1L, tarefaDTO);

        assertNotNull(resultado);
        assertEquals(tarefaDTO.getTitulo(), resultado.getTitulo());
        verify(tarefaRepositorio, times(1)).getReferenceById(anyLong());
        verify(tarefaRepositorio, times(1)).save(any(Tarefa.class));
    }

    @Test
    void atualizar_lancarExcecaoTarefaNaoEncontrada() {
        when(tarefaRepositorio.getReferenceById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(ExcecaoRecursoNaoEncontrado.class, () -> tarefaServico.atualizar(1L, tarefaDTO));
    }

    @Test
    void deletar() {
        doNothing().when(tarefaRepositorio).deleteById(anyLong());

        assertDoesNotThrow(() -> tarefaServico.deletar(1L));

        verify(tarefaRepositorio, times(1)).deleteById(anyLong());
    }
}
