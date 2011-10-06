/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.controllers.Command;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class ExcluirTabelaBotao extends Botao {

    Command command = null;

    public ExcluirTabelaBotao() {
        super();
        command = new ExcluirTabelaCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
