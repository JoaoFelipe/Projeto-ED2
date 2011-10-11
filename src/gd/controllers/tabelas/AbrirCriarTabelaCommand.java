/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.tabelas;

import gd.controllers.OpenDialogCommand;
import javax.swing.JDialog;
import gd.views.CriarTabelaView;

/**
 *
 * @author Joao
 */
public class AbrirCriarTabelaCommand extends OpenDialogCommand{

    public AbrirCriarTabelaCommand() {
        super();
    }

    public JDialog instanciarJanela() {
        return new CriarTabelaView(mainFrame);
    }



}
