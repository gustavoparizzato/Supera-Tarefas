package org.gp.tarefas_supera.controles;

import org.gp.tarefas_supera.dto.TarefaDTO;
import org.gp.tarefas_supera.servicos.TarefaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/tarefas")
public class TarefaControle {

    @Autowired
    private TarefaServico tarefaServico;

    @GetMapping
    public ResponseEntity<Page<TarefaDTO>> buscarTodasTarefas(
            @RequestParam(value = "tarefaId", required = false) Long tarefaId,
            @RequestParam(value = "titulo", required = false) String titulo,
            @RequestParam(value = "descricao", required = false) String descricao,
            Pageable pageable) {
        Page<TarefaDTO> resultado = tarefaServico.buscarTodas(tarefaId, titulo, descricao, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> inserir(@RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefa = tarefaServico.inserir(tarefaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tarefaId}")
                .buildAndExpand(tarefa.getTarefaId()).toUri();
        return ResponseEntity.created(uri).body(tarefa);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TarefaDTO> atualizar(@PathVariable Long id, @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefa = tarefaServico.atualizar(id, tarefaDTO);
        return ResponseEntity.ok().body(tarefa);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tarefaServico.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
