package org.gp.tarefas_supera.servicos.excecao;

public class ExcecaoBancoDeDados extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ExcecaoBancoDeDados(String msg){ super(msg); }
}
