package org.gp.tarefas_supera.repositorios;

import org.gp.tarefas_supera.dto.TarefaListaDTO;
import org.gp.tarefas_supera.entidades.TarefaLista;
import org.gp.tarefas_supera.enums.ItemEstado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TarefaListaRepositorio extends JpaRepository<TarefaLista, Long> {

    @Query("SELECT tl FROM TarefaLista tl WHERE tl.tarefa.tarefaId = :tarefaId ORDER BY tl.prioridade DESC")
    Page<TarefaListaDTO> buscarPorTarefaId(Long tarefaId, Pageable pageable);

    @Query("SELECT tl " +
            "FROM TarefaLista tl " +
            "WHERE (tl.titulo LIKE CONCAT('%', :tituloItem, '%') OR :tituloItem IS NULL) " +
            "AND (tl.itemEstado = :itemEstado OR :itemEstado IS NULL) " +
            "ORDER BY tl.prioridade DESC")
    Page<TarefaListaDTO> buscarTodas(String tituloItem, ItemEstado itemEstado, Pageable pageable);
}
