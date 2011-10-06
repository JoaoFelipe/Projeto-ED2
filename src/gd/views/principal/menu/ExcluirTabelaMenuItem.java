/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.controllers.FecharCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class ExcluirTabelaMenuItem extends ItemDeMenu {

    Command excluirTabelaCommand = null;

    public ExcluirTabelaMenuItem() {
        super();
        this.setText("Excluir Tabela");
        excluirTabelaCommand = new ExcluirTabelaCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        excluirTabelaCommand.execute();
    }

}
