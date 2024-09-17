package org.gp.tarefas_supera.servicos;

import jakarta.persistence.EntityNotFoundException;
import org.gp.tarefas_supera.dto.TarefaListaDTO;
import org.gp.tarefas_supera.entidades.Tarefa;
import org.gp.tarefas_supera.entidades.TarefaLista;
import org.gp.tarefas_supera.enums.ItemEstado;
import org.gp.tarefas_supera.repositorios.TarefaListaRepositorio;
import org.gp.tarefas_supera.repositorios.TarefaRepositorio;
import org.gp.tarefas_supera.servicos.excecao.ExcecaoBancoDeDados;
import org.gp.tarefas_supera.servicos.excecao.ExcecaoRecursoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarefaListaServico {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Autowired
    private TarefaListaRepositorio tarefaListaRepositorio;

    @Cacheable("itens")
    @Transactional(readOnly = true)
    public Page<TarefaListaDTO> buscarTodos(Long tarefaId, String tituloItem, ItemEstado itemEstado, Pageable pageable) {
        Page<TarefaListaDTO> resultado = Page.empty();
        if (tarefaId != null) {
            resultado = tarefaListaRepositorio.buscarPorTarefaId(tarefaId, pageable);
        } else {
            resultado = tarefaListaRepositorio.buscarTodas(tituloItem, itemEstado, pageable);
        }
        return resultado;
    }

    @CacheEvict(value = "itens", allEntries = true)
    @Transactional
    public TarefaListaDTO addTarefaList(Long tarefaId, TarefaListaDTO obj) {
        try {
            Tarefa tarefa = tarefaRepositorio.getReferenceById(tarefaId);
            TarefaLista tarefaLista = new TarefaLista();
            atualizarDadosItem(tarefaLista, obj);
            tarefaLista.setTarefa(tarefa);
            tarefa.getTarefas().add(tarefaLista);
            tarefaListaRepositorio.save(tarefaLista);
            return new TarefaListaDTO(tarefaLista);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @CacheEvict(value = "itens", allEntries = true)
    @Transactional
    public TarefaListaDTO atualizarItemLista(Long tarefaListaId, TarefaListaDTO obj) {
        try {
            TarefaLista tarefaLista = tarefaListaRepositorio.getReferenceById(tarefaListaId);
            atualizarDadosItem(tarefaLista, obj);
            return new TarefaListaDTO(tarefaListaRepositorio.save(tarefaLista));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Item " + tarefaListaId + " não encontrado");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @CacheEvict(value = "itens", allEntries = true)
    public void removeItemList(Long tarefaListaId) {
        try {
            tarefaListaRepositorio.deleteById(tarefaListaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Item " + tarefaListaId + " não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    private void atualizarDadosItem(TarefaLista tarefaLista, TarefaListaDTO obj) {
        if (tarefaLista.getTarefaListaId() == null) {
            tarefaLista.setItemEstado(ItemEstado.PENDENTE);
        } else {
            if (obj.getItemEstado() != null) {
                tarefaLista.setItemEstado(obj.getItemEstado());
            } else {
                tarefaLista.setItemEstado(tarefaLista.getItemEstado());
            }
        }
        tarefaLista.setTitulo(obj.getTitulo());
        tarefaLista.setDescricao(obj.getDescricao());
        tarefaLista.setPrioridade(obj.getPrioridade().equalsIgnoreCase("SIM") ? Boolean.TRUE : Boolean.FALSE);
    }
}
