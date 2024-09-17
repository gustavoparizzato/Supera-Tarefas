package org.gp.tarefas_supera.servicos.excecao;

public class ExcecaoRecursoNaoEncontrado extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcecaoRecursoNaoEncontrado(String msg){
        super(msg);
    }
}
