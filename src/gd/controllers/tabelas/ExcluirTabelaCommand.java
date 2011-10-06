/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.tabelas;

import gd.controllers.Command;
import gd.exceptions.ModelException;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.views.principal.TabelasLista;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class ExcluirTabelaCommand extends Command{

    public ExcluirTabelaCommand() {
    }

    @Override
    public void execute(Object... arg) {
        TabelasLista tabela = TabelasLista.getInstancia();

        int indice = tabela.getSelectedIndex();
        if (indice > -1) {
            try {
                ListaER inst = ListaER.getInstancia();
                String nome = (String) tabela.getModel().getElementAt(indice);
                inst.removePorNome(nome);
                inst.grava();
            } catch (ModelException ex) {
                ex.execute();
            }
            tabela.atualizar();
        }
    }



}
