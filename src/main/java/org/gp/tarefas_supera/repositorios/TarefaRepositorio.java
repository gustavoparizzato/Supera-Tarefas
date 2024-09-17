package org.gp.tarefas_supera.repositorios;

import org.gp.tarefas_supera.dto.TarefaDTO;
import org.gp.tarefas_supera.entidades.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepositorio extends JpaRepository<Tarefa, Long> {

    @Query("SELECT t " +
            "FROM Tarefa t " +
            "LEFT JOIN t.tarefas i " +
            "WHERE (t.tarefaId = :tarefaId OR :tarefaId IS NULL) " +
            "AND (t.titulo LIKE CONCAT('%', :tituloLista, '%') OR :tituloLista IS NULL) " +
            "AND (i.titulo LIKE CONCAT('%', :tituloItem, '%') OR :tituloItem IS NULL) " +
            "ORDER BY t.tarefaId DESC")
    Page<TarefaDTO> buscarTodas(Long tarefaId, String tituloLista, String tituloItem, Pageable pageable);
}
