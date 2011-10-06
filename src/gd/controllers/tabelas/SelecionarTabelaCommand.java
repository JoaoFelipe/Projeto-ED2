/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.controllers.tabelas;

import gd.controllers.Command;
import gd.exceptions.ModelException;
import gd.exceptions.NonUniqueException;
import gd.exceptions.NotFoundException;
import gd.models.Colecao;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.models.ER.Relacionamento;
import gd.models.atributos.ColecaoAtributo;
import gd.views.Lista;
import gd.views.ModeloLista;
import gd.views.principal.EntidadeTabela;
import gd.views.principal.TabelasLista;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class SelecionarTabelaCommand extends Command {

    static boolean using = false; // só permitir executar 1 vez

    public SelecionarTabelaCommand() {
        super();
    }

    @Override
    public void execute(Object... arg) {
        try {
            EntidadeRelacionamento er = null;
            int i = TabelasLista.getInstancia().getSelectedIndex();
            if (ListaER.getInstancia().getLista().size() > i && i > -1){
                er = ListaER.getInstancia().getLista().get(i);
            }
                selecionarER(er);
        } catch (ModelException ex) {
            ex.execute();
        }

    }

    public void selecionarER(EntidadeRelacionamento e){
        EntidadeTabela tabela = EntidadeTabela.getInstancia();
        tabela.setEntidadeRelacionamento(e);
        if (e == null){
            tabela.setModel(Arrays.asList("Selecione uma tabela"));  
        } else if (e instanceof Entidade) {
            Entidade entidade = (Entidade) e;
            tabela.setModel((List<String>) Colecao.processar(
                    entidade.getAtributos(),
                    ColecaoAtributo.processos.getNome()
            ));
            tabela.getPainel().setVisible(true);
        } else {
            Relacionamento relacionamento = (Relacionamento) e;
            tabela.setModel(Arrays.asList("Tabela", "Atributo", "Tabela Referenciada", "Código", "Atributo de Busca"));
            tabela.getModelo().insertRow(0, relacionamento.getRow());
            tabela.getPainel().setVisible(false);
        }

    }
}
