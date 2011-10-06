/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal.menu;

import gd.views.ItemDeMenu;
import gd.controllers.Command;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.FecharCommand;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class CriarReferenciaMenuItem extends ItemDeMenu {

    Command criarReferenciaCommand = null;

    public CriarReferenciaMenuItem() {
        super();
        this.setText("Criar Referência");
        criarReferenciaCommand = new AbrirCriarReferenciaCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        criarReferenciaCommand.execute();
    }

}
