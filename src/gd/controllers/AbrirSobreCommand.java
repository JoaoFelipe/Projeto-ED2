/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.views.CriarReferenciaView;
import gd.views.SobreView;
import javax.swing.JDialog;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class AbrirSobreCommand extends OpenDialogCommand{

    public AbrirSobreCommand() {
        super();
    }

    public JDialog instanciarJanela() {
        return new SobreView(mainFrame);
    }



}
