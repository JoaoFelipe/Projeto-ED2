/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.Command;
import gd.controllers.CriarEntidadeController;
import gd.controllers.TabelasController;
import gd.models.atributos.Atributo;
import gd.views.base.ComboBoxEditavel;
import gd.views.base.ComboBoxNaoEditavel;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Joao
 */
public class TabelasCombo extends ComboBoxNaoEditavel {

    Command listarEntidadesCommand = null;
    JComboBox comboAtributos = null;


    public TabelasCombo(JComboBox comboAtributos) {
        super();
        this.comboAtributos = comboAtributos;
        TabelasController.entidadesToComboBoxModel((DefaultComboBoxModel) this.getModel());
    }

     public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        TabelasController.atributosToComboBoxModel(
            (String) this.getModel().getSelectedItem(),
            (DefaultComboBoxModel) comboAtributos.getModel()
        );
    }

}
