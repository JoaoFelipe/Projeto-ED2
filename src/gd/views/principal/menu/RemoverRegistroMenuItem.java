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
import gd.controllers.tabela.AbrirRemoverRegistrosCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class RemoverRegistroMenuItem extends ItemDeMenu {

    Command removerRegistroCommand = null;

    public RemoverRegistroMenuItem() {
        super();
        this.setText("Remover Registros");
        removerRegistroCommand = new AbrirRemoverRegistrosCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        removerRegistroCommand.execute();
    }

}
