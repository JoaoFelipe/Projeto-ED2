/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.controllers.FecharCommand;
import gd.controllers.tabela.AbrirInserirRegistroCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class InserirRegistroMenuItem extends ItemDeMenu {

    Command inserirRegistroCommand = null;

    public InserirRegistroMenuItem() {
        super();
        this.setText("Inserir Registro");
        inserirRegistroCommand = new AbrirInserirRegistroCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        inserirRegistroCommand.execute();
    }

}
