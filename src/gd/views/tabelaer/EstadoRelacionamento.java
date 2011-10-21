package gd.views.tabelaer;

import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.Relacionamento;
import java.util.Arrays;

public class EstadoRelacionamento implements EstadoTabela {

    private Relacionamento relacionamento = null;
    private TabelaPrincipal tabela = null;

    public EstadoRelacionamento(TabelaPrincipal tabela, EntidadeRelacionamento relacionamento) {
        this.tabela = tabela;
        this.relacionamento = (Relacionamento) relacionamento;
    }

    public void show() {
        getTabela().setModelo(Arrays.asList("Tabela", "Atributo", "Tabela Referenciada", "Código"), null, false);
        getTabela().getModelo().addRow(getRelacionamento().getRow());
        getTabela().getPainel().setVisible(false);
    }

    public EntidadeRelacionamento getEr() {
        return getRelacionamento();
    }

    public Relacionamento getRelacionamento() {
        return relacionamento;
    }

    public void setRelacionamento(Relacionamento relacionamento) {
        this.relacionamento = relacionamento;
    }

    public TabelaPrincipal getTabela() {
        return tabela;
    }

    public void setTabela(TabelaPrincipal tabela) {
        this.tabela = tabela;
    }

}
