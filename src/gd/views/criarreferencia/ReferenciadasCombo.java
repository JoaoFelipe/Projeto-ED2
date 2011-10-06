/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criarreferencia;

import gd.controllers.Command;
import gd.controllers.criarreferencia.CarregarAtributosCommand;
import gd.controllers.criarreferencia.ListarEntidadesCommand;
import gd.views.criartabela.*;
import gd.models.atributos.Atributo;
import gd.views.ComboBoxEditavel;
import gd.views.ComboBoxNaoEditavel;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Joao
 */
public class ReferenciadasCombo extends ComboBoxNaoEditavel {

    Command atualizarCodCommand = null;
    Command atualizarBuscaCommand = null;
    Command listarEntidadesCommand = null;


    public ReferenciadasCombo(JComboBox comboCod, JComboBox comboBusca) {
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
