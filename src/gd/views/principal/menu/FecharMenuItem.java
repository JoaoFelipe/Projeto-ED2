/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.FecharCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class FecharMenuItem extends ItemDeMenu {

    Command fecharCommand = null;

    public FecharMenuItem() {
        super();
        this.setText("Sair");
        fecharCommand = new FecharCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        fecharCommand.execute();
    }

}
