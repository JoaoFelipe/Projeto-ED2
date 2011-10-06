/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.criarreferencia;

import gd.controllers.Command;
import gd.exceptions.ModelException;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import gd.models.atributos.Atributo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Joao
 */
public class CarregarAtributosCommand extends Command{

    DefaultComboBoxModel modeloOriginal = null;
    DefaultComboBoxModel modeloDerivado = null;

    public CarregarAtributosCommand(DefaultComboBoxModel modeloOriginal, DefaultComboBoxModel modeloDerivado){
        super();
        this.modeloOriginal = modeloOriginal;
        this.modeloDerivado = modeloDerivado;
    }


    @Override
    public void execute(Object... arg) {
        try {
            modeloDerivado.removeAllElements();
            Entidade e =(Entidade) ListaER.getInstancia().buscar((String) modeloOriginal.getSelectedItem());
            List<Atributo> temp = e.getAtributos();
            for (int i = 0; i < temp.size(); i++) {
                modeloDerivado.insertElementAt(temp.get(i).getNome(), i);
            }
        } catch (ModelException ex) {
            ex.execute();
        }
    }


}
