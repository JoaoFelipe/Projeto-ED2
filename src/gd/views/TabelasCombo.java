package gd.views;

import gd.controllers.Command;
import gd.controllers.TabelasController;
import gd.views.base.ComboBoxNaoEditavel;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class TabelasCombo extends ComboBoxNaoEditavel {

    private Command listarEntidadesCommand = null;
    private JComboBox comboAtributos = null;

    public TabelasCombo(JComboBox comboAtributos) {
        super();
        this.comboAtributos = comboAtributos;
        TabelasController.entidadesToComboBoxModel((DefaultComboBoxModel) this.getModel());
    }

    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        TabelasController.atributosToComboBoxModel(
            (String) this.getModel().getSelectedItem(),
            (DefaultComboBoxModel) getComboAtributos().getModel()
        );
    }

    public Command getListarEntidadesCommand() {
        return listarEntidadesCommand;
    }

    public void setListarEntidadesCommand(Command listarEntidadesCommand) {
        this.listarEntidadesCommand = listarEntidadesCommand;
    }

    public JComboBox getComboAtributos() {
        return comboAtributos;
    }

    public void setComboAtributos(JComboBox comboAtributos) {
        this.comboAtributos = comboAtributos;
    }

}
