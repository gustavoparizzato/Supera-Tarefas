package org.gp.tarefas_supera.servicos;

import jakarta.persistence.EntityNotFoundException;
import org.gp.tarefas_supera.dto.TarefaDTO;
import org.gp.tarefas_supera.entidades.Tarefa;
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
public class TarefaServico {

    @Autowired
    private TarefaRepositorio tarefaRepositorio;

    @Cacheable("tarefas")
    @Transactional(readOnly = true)
    public Page<TarefaDTO> buscarTodas(Long tarefaId, String tituloLista, String tituloItem, Pageable pageable) {
        return tarefaRepositorio.buscarTodas(tarefaId, tituloLista, tituloItem, pageable);
    }

    @CacheEvict(value = "tarefas", allEntries = true)
    @Transactional
    public TarefaDTO inserir(TarefaDTO obj) {
        try {
            Tarefa tarefa = new Tarefa();
            atualizarDadosTarefa(tarefa, obj);
            return new TarefaDTO(tarefaRepositorio.save(tarefa));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @CacheEvict(value = "tarefas", allEntries = true)
    @Transactional
    public TarefaDTO atualizar(Long tarefaId, TarefaDTO obj) {
        try {
            Tarefa tarefa = tarefaRepositorio.getReferenceById(tarefaId);
            atualizarDadosTarefa(tarefa, obj);
            return new TarefaDTO(tarefaRepositorio.save(tarefa));
        } catch (EntityNotFoundException e) {
            throw new ExcecaoRecursoNaoEncontrado("Tarefa " + tarefaId + " não encontrado");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @CacheEvict(value = "tarefas", allEntries = true)
    public void deletar(Long tarefaId) {
        try {
            tarefaRepositorio.deleteById(tarefaId);
        } catch (EmptyResultDataAccessException e) {
            throw new ExcecaoRecursoNaoEncontrado("Tarefa " + tarefaId + " não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new ExcecaoBancoDeDados(e.getMessage());
        }
    }

    private void atualizarDadosTarefa(Tarefa tarefa, TarefaDTO obj) {
        tarefa.setTitulo(obj.getTitulo());
        tarefa.setDescricao(obj.getDescricao());
    }
}