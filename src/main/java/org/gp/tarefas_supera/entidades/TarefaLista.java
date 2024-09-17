package org.gp.tarefas_supera.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.gp.tarefas_supera.enums.ItemEstado;

import java.io.Serializable;

@Entity
@Table(name = "tarefa_lista")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="tarefaListaId")
public class TarefaLista implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tarefaListaId;
    private String titulo;
    private String descricao;
    private Integer itemEstado;
    private Boolean prioridade;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tarefa_id")
    private Tarefa tarefa;

    public ItemEstado getItemEstado() { return ItemEstado.codigoEstado(itemEstado); }

    public void setItemEstado(ItemEstado itemEstado) {
        if (itemEstado != null) {
            this.itemEstado = itemEstado.getCodigo();
        }
    }
}
