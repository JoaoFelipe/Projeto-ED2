/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.FecharCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class CriarTabelaMenuItem extends ItemDeMenu {

    Command criarTabelaCommand = null;

    public CriarTabelaMenuItem() {
        super();
        this.setText("Criar Tabela");
        criarTabelaCommand = new AbrirCriarTabelaCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        criarTabelaCommand.execute();
    }

}
