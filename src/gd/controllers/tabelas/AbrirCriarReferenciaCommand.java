/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.tabelas;

import gd.controllers.Command;
import gd.controllers.OpenDialogCommand;
import gd.views.CriarReferenciaView;
import javax.swing.JDialog;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class AbrirCriarReferenciaCommand extends OpenDialogCommand{

    public AbrirCriarReferenciaCommand() {
        super();
    }

    public JDialog instanciarJanela() {
        return new CriarReferenciaView(mainFrame);
    }


}
