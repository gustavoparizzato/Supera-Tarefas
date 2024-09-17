package org.gp.tarefas_supera.servicos;

import jakarta.persistence.EntityNotFoundException;
import org.gp.tarefas_supera.dto.TarefaListaDTO;
import org.gp.tarefas_supera.entidades.Tarefa;
import org.gp.tarefas_supera.entidades.TarefaLista;
import org.gp.tarefas_supera.enums.ItemEstado;
import org.gp.tarefas_supera.repositorios.TarefaListaRepositorio;
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

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarefaListaServicoTest {

    @Mock
    private TarefaListaRepositorio tarefaListaRepositorio;

    @Mock
    private TarefaRepositorio tarefaRepositorio;

    @InjectMocks
    private TarefaListaServico tarefaListaServico;

    private TarefaLista tarefaLista;
    private TarefaListaDTO tarefaListaDTO;
    private Tarefa tarefa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tarefa = new Tarefa();
        tarefa.setTarefas(new ArrayList<>());

        tarefaLista = new TarefaLista();
        tarefaLista.setTitulo("Item Teste");
        tarefaLista.setDescricao("Descrição Teste");
        tarefaLista.setPrioridade(Boolean.TRUE);
        tarefaLista.setItemEstado(ItemEstado.PENDENTE);

        tarefaListaDTO = new TarefaListaDTO(tarefaLista);

        when(tarefaRepositorio.getReferenceById(anyLong())).thenReturn(tarefa);
    }

    @Test
    void buscarTodos() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(tarefaListaRepositorio.buscarPorTarefaId(anyLong(), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(tarefaListaDTO)));

        Page<TarefaListaDTO> result = tarefaListaServico.buscarTodos(1L, null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(tarefaListaRepositorio, times(1)).buscarPorTarefaId(anyLong(), eq(pageable));
    }

    @Test
    void addTarefaList() {
        when(tarefaRepositorio.getReferenceById(anyLong())).thenReturn(tarefa);
        when(tarefaListaRepositorio.save(any(TarefaLista.class))).thenReturn(tarefaLista);

        TarefaListaDTO resultado = tarefaListaServico.addTarefaList(1L, tarefaListaDTO);

        assertNotNull(resultado);
        assertEquals(tarefaListaDTO.getTitulo(), resultado.getTitulo());

        verify(tarefaListaRepositorio, times(1)).save(any(TarefaLista.class));
        verify(tarefaRepositorio, times(1)).getReferenceById(anyLong());
    }

    @Test
    void atualizarItemLista() {
        when(tarefaListaRepositorio.getReferenceById(anyLong())).thenReturn(tarefaLista);
        when(tarefaListaRepositorio.save(any(TarefaLista.class))).thenReturn(tarefaLista);

        TarefaListaDTO resultado = tarefaListaServico.atualizarItemLista(1L, tarefaListaDTO);

        assertNotNull(resultado);
        assertEquals(tarefaListaDTO.getTitulo(), resultado.getTitulo());
        verify(tarefaListaRepositorio, times(1)).getReferenceById(anyLong());
        verify(tarefaListaRepositorio, times(1)).save(any(TarefaLista.class));
    }

    @Test
    void atualizarItemLista_lancarExcecaoItemNaoEncontrado() {
        when(tarefaListaRepositorio.getReferenceById(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(ExcecaoRecursoNaoEncontrado.class, () -> tarefaListaServico.atualizarItemLista(1L, tarefaListaDTO));
    }

    @Test
    void removeItemList() {
        doNothing().when(tarefaListaRepositorio).deleteById(anyLong());

        assertDoesNotThrow(() -> tarefaListaServico.removeItemList(1L));

        verify(tarefaListaRepositorio, times(1)).deleteById(anyLong());
    }
}