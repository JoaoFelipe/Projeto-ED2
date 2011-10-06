/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.controllers.tabela.AbrirModificarRegistrosCommand;
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
public class AtualizarRegistroMenuItem extends ItemDeMenu {

    Command atualizarRegistroCommand = null;

    public AtualizarRegistroMenuItem() {
        super();
        this.setText("Atualizar Registros");
        atualizarRegistroCommand = new AbrirModificarRegistrosCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        atualizarRegistroCommand.execute();
    }

}
