/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.tabela.AbreConsultarRegistrosCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.controllers.FecharCommand;
import gd.controllers.tabela.AbrirInserirRegistroCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class ConsultarRegistroMenuItem extends ItemDeMenu {

    Command consultarRegistroCommand = null;

    public ConsultarRegistroMenuItem() {
        super();
        this.setText("Consultar Registros");
        consultarRegistroCommand = new AbreConsultarRegistrosCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        consultarRegistroCommand.execute();
    }

}
