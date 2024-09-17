package org.gp.tarefas_supera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gp.tarefas_supera.enums.ItemEstado;
import org.gp.tarefas_supera.entidades.TarefaLista;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TarefaListaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tarefaListaId;
    @NotBlank(message = "O Título do Item não pode ser vazio ou nulo!")
    @Size(min = 5, message = "O Título do Item deve conter pelo menos 3 caracteres!")
    private String titulo;
    private String descricao;
    @NotBlank(message = "Estado do Item não pode ser vazio ou nulo")
    private Integer itemEstado;
    private Boolean prioridade;

    public TarefaListaDTO(TarefaLista tarefaLista) { BeanUtils.copyProperties(tarefaLista, this); }

    public String getPrioridade() { return this.prioridade ? "Sim" : "Não"; }

    public ItemEstado getItemEstado() { return ItemEstado.codigoEstado(itemEstado); }

    public void setItemEstado(ItemEstado itemEstado) {
        if (itemEstado != null) {
            this.itemEstado = itemEstado.getCodigo();
        }
    }
}
