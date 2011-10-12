/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.tabela;

import gd.controllers.Command;
import gd.controllers.OpenDialogCommand;
import gd.views.ModificarView;
import gd.views.SobreView;
import javax.swing.JDialog;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class AbrirModificarRegistrosCommand extends OpenDialogCommand{

    public AbrirModificarRegistrosCommand() {
        super();
    }

    public JDialog instanciarJanela() {
        return new ModificarView(mainFrame);
    }




}
