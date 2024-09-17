package org.gp.tarefas_supera.enums;

public enum ItemEstado {

    PENDENTE (0),
    EM_ANDAMENTO (1),
    REALIZADO (2);

    private int codigo;

    private ItemEstado(int codigo) { this.codigo = codigo; }

    public int getCodigo() { return this.codigo; }

    public static ItemEstado codigoEstado(int codigo) {
        for (ItemEstado itemEstado : ItemEstado.values()) {
            if (itemEstado.getCodigo() == codigo) {
                return itemEstado;
            }
        }
        throw new IllegalArgumentException("Estado de Item não Existe");
    }
}
