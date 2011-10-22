package gd.views.tabelaer;

import gd.models.ER.EntidadeRelacionamento;
import java.util.Arrays;

public class EstadoVazio implements EstadoTabela {

    private TabelaPrincipal tabela = null;

    public EstadoVazio(TabelaPrincipal tabela) {
        this.tabela = tabela;
    }

    public void show() {
        getTabela().setModelo(Arrays.asList("Selecione uma tabela"), null, false);
        getTabela().getPainel().setVisible(false);
    }

    public EntidadeRelacionamento getEr() {
        return null;
    }

    public TabelaPrincipal getTabela() {
        return tabela;
    }

    public void setTabela(TabelaPrincipal tabela) {
        this.tabela = tabela;
    }
    
}
