package gd.views.tabelaer;

import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.atributos.ColecaoAtributo;
import java.util.List;

public class EstadoEntidade implements EstadoTabela {

    Entidade entidade = null;
    TabelaPrincipal tabela = null;

    public EstadoEntidade(TabelaPrincipal tabela, EntidadeRelacionamento entidade) {
        this.tabela = tabela;
        this.entidade = (Entidade) entidade;
    }

    public void show() {
        tabela.setModelo(
           (List<String>) Colecao.processar(entidade.getAtributos(), ColecaoAtributo.processos.getNome()),
           (List<Class>) Colecao.processar(entidade.getAtributos(), ColecaoAtributo.processos.getClasse()),
           true
        );
        tabela.getPainel().setVisible(true);
    }

    public EntidadeRelacionamento getEr() {
        return entidade;
    }

}
