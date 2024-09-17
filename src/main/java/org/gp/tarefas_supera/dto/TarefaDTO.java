package org.gp.tarefas_supera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gp.tarefas_supera.entidades.Tarefa;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TarefaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long tarefaId;
    @NotBlank(message = "O Título da Lista não pode ser vazio ou nulo!")
    @Size(min = 5, message = "O Título da Lista deve conter pelo menos 3 caracteres!")
    private String titulo;
    private String descricao;

    public TarefaDTO(Tarefa tarefa) { BeanUtils.copyProperties(tarefa, this); }
}
