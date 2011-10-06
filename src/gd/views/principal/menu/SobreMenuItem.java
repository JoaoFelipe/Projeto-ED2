/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.controllers.AbrirSobreCommand;
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
public class SobreMenuItem extends ItemDeMenu {

    Command abreSobreCommand = null;

    public SobreMenuItem() {
        super();
        this.setText("Sobre");
        abreSobreCommand = new AbrirSobreCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        abreSobreCommand.execute();
    }

}
