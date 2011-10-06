/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.criarreferencia;

import gd.controllers.Command;
import gd.exceptions.ModelException;
import gd.models.ER.ListaER;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Joao
 */
public class ListarEntidadesCommand extends Command{

    DefaultComboBoxModel modelo = null;

    public ListarEntidadesCommand(DefaultComboBoxModel modelo){
        super();
        this.modelo = modelo;
    }


    @Override
    public void execute(Object... arg) {
        try {
            modelo.removeAllElements();
            List<String> temp;
            temp = ListaER.getInstancia().getNomesTabelas();
            for (int i = 0; i < temp.size(); i++) {
                modelo.insertElementAt(temp.get(i), i);
            }
        } catch (ModelException ex) {
            ex.execute();
        }
    }


}
