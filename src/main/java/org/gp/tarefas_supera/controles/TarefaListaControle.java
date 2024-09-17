package org.gp.tarefas_supera.controles;

import org.gp.tarefas_supera.dto.TarefaListaDTO;
import org.gp.tarefas_supera.enums.ItemEstado;
import org.gp.tarefas_supera.servicos.TarefaListaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/tarefas")
public class TarefaListaControle {

    @Autowired
    private TarefaListaServico tarefaListaServico;

    @GetMapping(value = "/{tarefaId}/itens")
    public ResponseEntity<Page<TarefaListaDTO>> buscarItens(
            @PathVariable Long tarefaId,
            @RequestParam(value = "tarefaListaId", required = false) Long tarefaListaId,
            @RequestParam(value = "titulo", required = false) String tituloItem,
            @RequestParam(value = "itemEstado", required = false) ItemEstado itemEstado,
            Pageable pageable) {
        Page<TarefaListaDTO> resultado = tarefaListaServico.buscarTodos(tarefaListaId, tituloItem, itemEstado, pageable);
        return ResponseEntity.ok().body(resultado);
    }

    @PostMapping(value = "/{tarefaId}/itens")
    public ResponseEntity<TarefaListaDTO> inserir(@PathVariable Long tarefaId, @RequestBody TarefaListaDTO tarefaListaDTO) {
        TarefaListaDTO tarefaLista = tarefaListaServico.addTarefaList(tarefaId, tarefaListaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tarefaId}")
                .buildAndExpand(tarefaLista.getTarefaListaId()).toUri();
        return ResponseEntity.created(uri).body(tarefaLista);
    }

    @PutMapping(value = "/{tarefaId}/itens/{tarefaListaId}")
    public ResponseEntity<TarefaListaDTO> atualizar(@PathVariable Long tarefaId, @PathVariable Long tarefaListaId, @RequestBody TarefaListaDTO tarefaListaDTO) {
        TarefaListaDTO tarefaLista = tarefaListaServico.atualizarItemLista(tarefaListaId, tarefaListaDTO);
        return ResponseEntity.ok().body(tarefaLista);
    }

    @DeleteMapping(value = "/itens/{tarefaListaId}")
    public ResponseEntity<Void> remover(@PathVariable Long tarefaListaId) {
        tarefaListaServico.removeItemList(tarefaListaId);
        return ResponseEntity.noContent().build();
    }
}
