/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.Command;
import gd.controllers.criarreferencia.CarregarAtributosCommand;
import gd.controllers.criarreferencia.ListarEntidadesCommand;
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
public class CriarReferenciaReferenciadasCombo extends ComboBoxNaoEditavel {

    Command atualizarCodCommand = null;
    Command atualizarBuscaCommand = null;
    Command listarEntidadesCommand = null;


    public CriarReferenciaReferenciadasCombo(JComboBox comboCod, JComboBox comboBusca) {
        super();
        atualizarCodCommand = new CarregarAtributosCommand(
            (DefaultComboBoxModel) this.getModel(),
            (DefaultComboBoxModel) comboCod.getModel()
        );
        atualizarBuscaCommand = new CarregarAtributosCommand(
            (DefaultComboBoxModel) this.getModel(),
            (DefaultComboBoxModel) comboBusca.getModel()
        );
        listarEntidadesCommand = new ListarEntidadesCommand(
            (DefaultComboBoxModel) this.getModel()
        );
        listarEntidadesCommand.execute();
    }

     public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        atualizarCodCommand.execute();
        atualizarBuscaCommand.execute();
    }

}
