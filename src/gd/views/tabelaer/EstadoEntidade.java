package gd.views.tabelaer;

import gd.controllers.TabelasController;
import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.arquivo.Arquivo;
import gd.models.arquivo.Consulta;
import gd.models.atributos.ColecaoAtributo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
        
        Arquivo arquivo = new Arquivo(entidade);
        try {
            Consulta consulta = new Consulta(arquivo, null);
            consulta.compila();
            TabelasController.registrosToTabelaModel(consulta, this.tabela.getModelo());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao abrir arquivo");
        }
                
        tabela.getPainel().setVisible(true);
    }


}
