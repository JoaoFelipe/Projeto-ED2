/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.models.atributos.Atributo;
import gd.views.base.ComboBoxEditavel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Joao
 */
public class CriarTabelaComboAtributos extends ComboBoxEditavel {

    DefaultComboBoxModel modelo = null;

    public CriarTabelaComboAtributos() {
        super();
        modelo = new DefaultComboBoxModel();
        this.setModel(modelo);
        listarAtributos();
    }

    public void listarAtributos() {
        modelo.removeAllElements();
        List<String> temp = Atributo.todasOpcoes();
        for (int i = 0; i < temp.size(); i++) {
            modelo.insertElementAt(temp.get(i), i);
        }


    }

}
